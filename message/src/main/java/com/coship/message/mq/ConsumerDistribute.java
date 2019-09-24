package com.coship.message.mq;

import com.alibaba.fastjson.JSONObject;
import com.coship.common.constants.Constants;
import com.coship.message.adapter.MessageAdapter;
import com.coship.message.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ConsumerDistribute {

    @Autowired
    private EmailService emailService;

    private MessageAdapter messageAdapter;

    // 监听消息
    @JmsListener(destination = "${messages.queue}")
    public void distribute(String json) {
        log.info("#####消息服务平台接受消息内容:{}#####", json);
        if (StringUtils.isEmpty(json)) {
            return;
        }
        JSONObject rootJSON = JSONObject.parseObject(json);
        JSONObject header = rootJSON.getJSONObject("header");
        String interfaceType = header.getString("interfaceType");

        if (StringUtils.isEmpty(interfaceType)) {
            return;
        }
        // 判断接口类型是否为发邮件
        if (interfaceType.equals(Constants.MSG_EMAIL)) {
            messageAdapter = emailService;
        }
        if (messageAdapter == null) {
            return;
        }
        JSONObject contentJson = rootJSON.getJSONObject("content");
        messageAdapter.sendMsg(contentJson);
    }

}