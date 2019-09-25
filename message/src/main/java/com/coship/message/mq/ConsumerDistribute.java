package com.coship.message.mq;

import com.alibaba.fastjson.JSONObject;
import com.coship.common.constants.Constants;
import com.coship.message.adapter.MessageAdapter;
import com.coship.message.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ConsumerDistribute {

    @Autowired
    private EmailService emailService;

    private MessageAdapter messageAdapter;

    public void sendEmail(String json){
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