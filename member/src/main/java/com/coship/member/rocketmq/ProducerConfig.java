package com.coship.member.rocketmq;

import java.util.HashSet;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: lipeng 910138
 * @Date: 2019/9/25 15:08
 */
@Slf4j
@Configuration
public class ProducerConfig {

    @Value("${rocketmq.producer.group}")
    private String groupName;

    @Value("${rocketmq.name-server}")
    private String namesrvAddr;
    /**
     * 消息最大大小，默认4M
     */
    @Value("${rocketmq.producer.maxMessageSize}")
    private Integer maxMessageSize;
    /**
     * 消息发送超时时间，默认3秒
     */
    @Value("${rocketmq.producer.sendMessageTimeout}")
    private Integer sendMsgTimeout;
    /**
     * 消息发送失败重试次数，默认2次
     */
    @Value("${rocketmq.producer.retryTimesWhenSendFailed}")
    private Integer retryTimesWhenSendFailed;

    @Value("${rocketmq.producer.retryTimesWhenSendAsyncFailed}")
    private Integer retryTimesWhenSendAsyncFailed;

    @Value("${rocketmq.producer.retryNextServer}")
    private boolean retryNextServer;

    @Bean
    public DefaultMQProducer getRocketMQProducer() {
        DefaultMQProducer producer = new DefaultMQProducer(this.groupName);
        producer.setNamesrvAddr(this.namesrvAddr);
        //如果需要同一个jvm中不同的producer往不同的mq集群发送消息，需要设置不同的instanceName
        //producer.setInstanceName(instanceName);
        producer.setMaxMessageSize(this.maxMessageSize);
        producer.setSendMsgTimeout(this.sendMsgTimeout);
        //如果发送消息失败，设置重试次数，默认为2次
        producer.setRetryTimesWhenSendFailed(this.retryTimesWhenSendFailed);

        producer.setRetryTimesWhenSendAsyncFailed(retryTimesWhenSendAsyncFailed);

        producer.setRetryAnotherBrokerWhenNotStoreOK(retryNextServer);

        try {
            producer.start();

            log.info(String.format("producer is start ! groupName:[%s],namesrvAddr:[%s]",
                    this.groupName, this.namesrvAddr));
        } catch (MQClientException e) {
            log.error(String.format("producer is error {}", e.getMessage(), e));
        }
        return producer;
    }

}