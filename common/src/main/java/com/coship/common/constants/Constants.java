package com.coship.common.constants;

public interface Constants {

    // 响应请求成功
    String HTTP_RES_CODE_200_VALUE = "success";

    // 系统错误
    String HTTP_RES_CODE_500_VALUE = "fail";

    // 响应请求成功code
    Integer HTTP_RES_CODE_200 = 200;

    //未关联QQ账号
    Integer HTTP_RES_CODE_201 = 201;
    // 系统错误
    Integer HTTP_RES_CODE_500 = 500;

    // 发送邮件
    String MSG_EMAIL = "email";

    // 会员token
    String TOKEN_MEMBER = "TOKEN_MEMBER";

    // 支付token
    String TOKEN_PAY = "TOKEN_PAY";

    //email消费者订阅的主题和tags("*"号表示订阅该主题下所有的tags),格式：topic~tag1||tag2||tag3;topic2~*;
    String MEMBER_REGISTER_TOPIC = "member_register_topic~*";

    // 用户有效期 90天
    Long TOKEN_MEMBER_TIME = (long) (60 * 60 * 24 * 90);

    Long PAY_TOKEN_MEMBER_TIME =(long)  (60 * 15 );

    String REGISTER = "register";

    String LOGIN = "login";

    String REDIRECT_INDEX = "redirect:/";

    String PAGE_INDEX = "index";

    String QQ_RELATION = "qqrelation";

    String QQ_OPENID_STR = "qqOpenid";

    int COOKIE_TOKEN_MEMBER_TIME = (60 * 60 * 24 * 90);

    // cookie 会员 totoken 名称
    String COOKIE_MEMBER_TOKEN = "cookie_member_token";
}