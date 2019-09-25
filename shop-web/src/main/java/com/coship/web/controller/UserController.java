package com.coship.web.controller;

import com.coship.api.member.entity.UserEntity;
import com.coship.common.base.ResponseBase;
import com.coship.common.constants.Constants;
import com.coship.common.utils.CookieUtil;
import com.coship.web.feign.MemberServiceFeign;
import com.qq.connect.QQConnectException;
import com.qq.connect.api.OpenID;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.oauth.Oauth;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: lipeng 910138
 * @Date: 2019/9/25 9:45
 */
@Controller
public class UserController {

    @Autowired
    private MemberServiceFeign serviceFeign;

    @GetMapping("/register")
    public String registerPage() {
        return Constants.REGISTER;
    }

    @PostMapping("/register")
    public String register(UserEntity userEntity, HttpServletRequest reqest) {
        ResponseBase responseBase = serviceFeign.regUser(userEntity);

        if (!Constants.HTTP_RES_CODE_200.equals(responseBase.getRtnCode())) {
            reqest.setAttribute("error", "注册失败");
            return Constants.REGISTER;
        }
        return Constants.LOGIN;
    }

    @GetMapping("/login")
    public String loginPage() {
        return Constants.LOGIN;
    }

    @PostMapping("/login")
    public String login(UserEntity userEntity, HttpServletRequest request,
            HttpServletResponse response) {
        ResponseBase result = serviceFeign.login(userEntity);
        if (!Constants.HTTP_RES_CODE_200.equals(result.getRtnCode())) {
            request.setAttribute("error", "账号或者密码错误");
            return Constants.LOGIN;
        }
        String token = result.getToken();

        if (StringUtils.isEmpty(token)) {
            request.setAttribute("error", "会话已经失效");
            return Constants.LOGIN;
        }

        // 3.将token信息存放在cookie里面
        CookieUtil.setUserCookie(token, response);
        return Constants.REDIRECT_INDEX;
    }

    @GetMapping("/")
    public String index(HttpServletRequest reqest) {
        // 1.从cookie中获取 token信息
        String token = CookieUtil.getUid(reqest, Constants.COOKIE_MEMBER_TOKEN);
        // 2.如果cookie 存在 token，调用会员服务接口，使用token查询用户信息
        if (StringUtils.isNotEmpty(token)) {
            ResponseBase responseBase = serviceFeign.findUserByToken(token);
            if (Constants.HTTP_RES_CODE_200.equals(responseBase.getRtnCode())) {
                UserEntity userEntity = (UserEntity) responseBase.getData();
                reqest.setAttribute("username", userEntity.getUsername());
            }
        }
        return Constants.PAGE_INDEX;
    }

    // 生成qq授权登录链接
    @RequestMapping("/locaQQLogin")
    public String locaQQLogin(HttpServletRequest reqest) throws QQConnectException {
        String authorizeURL = new Oauth().getAuthorizeURL(reqest);
        return "redirect:" + authorizeURL;
    }

    @RequestMapping("/qqLoginCallback")
    public String qqLoginCallback(HttpServletRequest reqest, HttpServletResponse response,
            HttpSession httpSession) throws QQConnectException {
        // 1.获取授权码COde
        // 2.使用授权码Code获取accessToken
        AccessToken accessTokenOj = new Oauth().getAccessTokenByRequest(reqest);
        if (accessTokenOj == null) {
            reqest.setAttribute("error", "QQ授权失败");
            return "error";
        }
        String accessToken = accessTokenOj.getAccessToken();
        if (Objects.isNull(accessToken)) {
            reqest.setAttribute("error", "accessToken为null");
            return "error";
        }
        // 3.使用accessToken获取openid
        OpenID openID = new OpenID(accessToken);
        String userOpenID = openID.getUserOpenID();
        // 4.调用会员服务接口 使用userOpenId 查找是否已经关联过账号
        ResponseBase resp = serviceFeign.findUserByOpenId(userOpenID);
        // 5.如果没有关联账号，跳转到关联账号页面
        if (Constants.HTTP_RES_CODE_201.equals(resp.getRtnCode())) {
            httpSession.setAttribute(Constants.QQ_OPENID_STR, userOpenID);
            return Constants.QQ_RELATION;
        }
        if (Constants.HTTP_RES_CODE_200.equals(resp.getRtnCode())) {
            CookieUtil.setUserCookie(resp.getToken(), response);
            return Constants.REDIRECT_INDEX;
        }
        return null;
    }

    //用户没有openId先关联
    @PostMapping("/qqRelation")
    public String qqRelation(UserEntity userEntity, HttpServletRequest request,
            HttpServletResponse response, HttpSession httpSession) {
        String openId = (String) request.getSession().getAttribute(Constants.QQ_OPENID_STR);
        if (StringUtils.isEmpty(openId)) {
            request.setAttribute("error", "没有获取到openid");
            return "error";
        }
        userEntity.setOpenid(openId);
        ResponseBase loginBase = serviceFeign.qqLogin(userEntity);
        if (!loginBase.getRtnCode().equals(Constants.HTTP_RES_CODE_200)) {
            //返回登录页
            request.setAttribute("error", "账号或者密码错误!");
            return Constants.LOGIN;
        }
        UserEntity loginedUser = (UserEntity) loginBase.getData();
        if (Objects.isNull(loginedUser) || StringUtils.isEmpty(loginBase.getToken())){
            //返回登录页
            request.setAttribute("error", "会话已经失效!");
            return Constants.LOGIN;
        }
        // 3.将token信息存放在cookie里面
        CookieUtil.setUserCookie(loginBase.getToken(), response);
        return Constants.REDIRECT_INDEX;
    }

}