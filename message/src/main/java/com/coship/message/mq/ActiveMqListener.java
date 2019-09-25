package com.coship.message.mq;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * @Author: lipeng 910138
 * @Date: 2019/9/25 15:45
 */
@Slf4j
@Component
public class ActiveMqListener {

    @Autowired
    private ConsumerDistribute distribute;

    // 监听消息
    @JmsListener(destination = "${messages.queue}")
    public void distribute(String json) {
        log.info("#####消息服务平台接受消息内容:{}#####", json);
        if (StringUtils.isEmpty(json)) {
            return;
        }
        distribute.sendEmail(json);
    }
}
