package com.coship.message.mq;

import com.coship.common.constants.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.UtilAll;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: lipeng 910138
 * @Date: 2019/9/25 15:25
 */
@Configuration
@Slf4j
public class MQConsumerConfiguration {

    @Value("${rocketmq.consumer.group}")
    private String groupName;

    @Value("${rocketmq.name-server}")
    private String namesrvAddr;

    @Value("${rocketmq.consumer.consumeThreadMin}")
    private int consumeThreadMin;

    @Value("${rocketmq.consumer.consumeThreadMax}")
    private int consumeThreadMax;

    @Value("${rocketmq.consumer.consumeMessageBatchMaxSize}")
    private int consumeMessageBatchMaxSize;

    @Autowired
    private ConsumerDistribute distribute;

    @Bean("emailMqConsumer")
    public DefaultMQPushConsumer getRocketMQConsumer() {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(groupName);
        consumer.setNamesrvAddr(namesrvAddr);
        consumer.setConsumeThreadMin(consumeThreadMin);
        consumer.setConsumeThreadMax(consumeThreadMax);
        consumer.setConsumeTimestamp(UtilAll.timeMillisToHumanString3(System.currentTimeMillis()));
        /**
         * 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
         * 如果非第一次启动，那么按照上次消费的位置继续消费
         */
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        /**
         * 设置消费模型，集群还是广播，默认为集群
         */
        consumer.setMessageModel(MessageModel.CLUSTERING);
        /**
         * 设置一次消费消息的条数，默认为1条
         */
        consumer.setConsumeMessageBatchMaxSize(consumeMessageBatchMaxSize);
        consumer.registerMessageListener((MessageListenerConcurrently) (list, context) -> {
            try {
                for (MessageExt messageExt : list) {

                    String messageBody = new String(messageExt.getBody(),
                            RemotingHelper.DEFAULT_CHARSET);

                    log.info("RocketMQ消费消息:" + messageBody);

                    distribute.sendEmail(messageBody);
                }
            } catch (Exception e) {
                log.error("消费消息失败:TOPIC:{}，异常：{}", Constants.MEMBER_REGISTER_TOPIC, e);
                return ConsumeConcurrentlyStatus.RECONSUME_LATER; //稍后再试
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS; //消费成功
        });
        String topics = Constants.MEMBER_REGISTER_TOPIC;
        try {
            /**
             * 设置该消费者订阅的主题和tag，如果是订阅该主题下的所有tag，则tag使用*；如果需要指定订阅该主题下的某些tag，则使用||分割，例如tag1||tag2||tag3
             */
            String[] topicTagsArr = topics.split(";");
            for (String topicTags : topicTagsArr) {
                String[] topicTag = topicTags.split("~");
                consumer.subscribe(topicTag[0], topicTag[1]);
            }
            consumer.start();
            log.info("consumer is start !!! groupName:{},topics:{},namesrvAddr:{}", groupName,
                    topics, namesrvAddr);
        } catch (MQClientException e) {
            log.error("consumer is start !!! groupName:{},topics:{},namesrvAddr:{}", groupName,
                    topics, namesrvAddr, e);
            throw new RuntimeException(e);
        }
        return consumer;
    }

}