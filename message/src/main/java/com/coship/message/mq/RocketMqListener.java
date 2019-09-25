//package com.coship.message.mq;
//
//import com.coship.common.constants.Constants;
//import java.nio.charset.StandardCharsets;
//import java.util.List;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
//import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
//import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
//import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
//import org.apache.rocketmq.common.UtilAll;
//import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
//import org.apache.rocketmq.common.message.MessageExt;
//import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
//import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
//import org.apache.rocketmq.spring.core.RocketMQListener;
//import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
//import org.springframework.stereotype.Component;
//
///**
// * @Author: lipeng 910138
// * @Date: 2019/9/25 14:10
// * spring2.0以上使用
// */
//@Component
//@Slf4j
//@RocketMQMessageListener(topic = Constants.MEMBER_REGISTER_TOPIC, consumerGroup = "${rocketmq.consumer.group}")
//public class RocketMqListener implements RocketMQListener<String>,
//        RocketMQPushConsumerLifecycleListener {
//
//    @Override
//    public void prepareStart(DefaultMQPushConsumer consumer) {
//        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_TIMESTAMP);
//        consumer.setConsumeTimestamp(UtilAll.timeMillisToHumanString3(System.currentTimeMillis()));
//        consumer.setMessageModel(MessageModel.CLUSTERING);
//        consumer.registerMessageListener(new MessageListenerOrderly() {
//            @Override
//            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> list,
//                    ConsumeOrderlyContext consumeOrderlyContext) {
//                for (MessageExt messageExt : list) {
//                    log.info("RocketMQ消费消息:" + new String(messageExt.getBody(),
//                            StandardCharsets.UTF_8));
//                }
//                return ConsumeOrderlyStatus.SUCCESS;
//            }
//        });
//    }
//
//    @Override
//    public void onMessage(String s) {
//
//    }
//
//}