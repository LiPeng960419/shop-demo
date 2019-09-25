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

    // 用户有效期 90天
    Long TOKEN_MEMBER_TIME = (long) (60 * 60 * 24 * 90);

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