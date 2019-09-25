package com.coship.member.rocketmq;

import com.coship.common.constants.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: lipeng 910138
 * @Date: 2019/9/25 14:01
 */
@Component
@Slf4j
public class RocketmqEmailSender {

    @Autowired
    private DefaultMQProducer mqProducer;

    public void sendEmail(String json) {
        try {
            Message message = new Message(Constants.MEMBER_REGISTER_TOPIC, json.getBytes());
            SendResult result = mqProducer.send(message);
            if (result != null && result.getSendStatus().compareTo(SendStatus.SEND_OK) == 0) {
                log.info("发送消息成功，TOPIC:{},消息ID：{}", Constants.MEMBER_REGISTER_TOPIC, result.getMsgId());
            } else {
                log.error("发送消息失败，TOPIC:{},消息内容：{}", Constants.MEMBER_REGISTER_TOPIC, json);
            }
        } catch (Exception e) {
            log.error("发送消息失败:{}，TOPIC:{}，异常：{}", json, Constants.MEMBER_REGISTER_TOPIC, e);
        }
    }

}