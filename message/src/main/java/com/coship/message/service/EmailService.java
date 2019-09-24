package com.coship.message.service;

import com.alibaba.fastjson.JSONObject;
import com.coship.message.adapter.MessageAdapter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

//处理第三方发送邮件
@Slf4j
@Service
public class EmailService implements MessageAdapter {

    @Value("${msg.subject}")
    private String subject;

    @Value("${msg.text}")
    private String text;

    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    private JavaMailSender mailSender; // 自动注入的Bean


    @Override
    public void sendMsg(JSONObject body) {
        // 处理发送邮件
        String mail = body.getString("email");
        if (StringUtils.isEmpty(mail)) {
            return;
        }
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        // 发送
        simpleMailMessage.setFrom(from);

        simpleMailMessage.setTo(mail);
        // 标题
        simpleMailMessage.setSubject(subject);
        // 内容
        simpleMailMessage.setText(text.replace("{}", mail));
        mailSender.send(simpleMailMessage);
        log.info("消息服务平台发送邮件:{}", mail);
    }

}
