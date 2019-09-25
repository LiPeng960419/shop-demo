package com.coship.member.api.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.coship.api.member.entity.UserEntity;
import com.coship.api.member.service.MemberService;
import com.coship.common.base.BaseApiService;
import com.coship.common.base.ResponseBase;
import com.coship.common.constants.Constants;
import com.coship.common.utils.MD5Util;
import com.coship.common.utils.TokenUtils;
import com.coship.member.dao.MemberDao;
import com.coship.member.mq.RegisterMailboxProducer;
import java.util.Date;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: lipeng 910138
 * @Date: 2019/9/24 16:00
 */
@Slf4j
@RestController
public class MemberServiceImpl extends BaseApiService implements MemberService {

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private RegisterMailboxProducer registerMailboxProducer;

    @Value("${messages.queue}")
    private String MESSAGESQUEUE;

    @Override
    public ResponseBase findUserById(Long userId) {
        UserEntity user = memberDao.findByID(userId);
        if (user == null) {
            return setResultError("未查找到用户信息.");
        }
        return setResultSuccess(user, null);
    }

    @Override
    public ResponseBase regUser(@RequestBody UserEntity user) {
        // 参数验证
        String password = user.getPassword();
        if (StringUtils.isEmpty(password)) {
            return setResultError("密码不能为空.");
        }
        String newPassword = MD5Util.MD5(password);
        user.setPassword(newPassword);
        user.setCreated(new Date());
        user.setUpdated(new Date());
        Integer result = memberDao.insertUser(user);
        if (result <= 0) {
            return setResultError("注册用户信息失败.");
        }
        // 采用异步方式发送消息
        if (StringUtils.isNotEmpty(user.getEmail())){
            String email = user.getEmail();
            String json = emailJson(email);
            log.info("####会员服务推送消息到消息服务平台####json:{}", json);
            sendMsg(json);
        }
        return setResultSuccess("用户注册成功.", null);
    }

    @Override
    public ResponseBase login(@RequestBody UserEntity user) {
        String username = user.getUsername();
        if (StringUtils.isEmpty(username)) {
            return setResultError("用户名不能为空.");
        }
        String password = user.getPassword();
        if (StringUtils.isEmpty(password)) {
            return setResultError("密码不能为空.");
        }
        password = MD5Util.MD5(password);
        UserEntity loginUser = memberDao.login(username, password);

        //登录
        return setLogin(loginUser);
    }

    private ResponseBase setLogin(UserEntity loginUser) {
        if (Objects.isNull(loginUser)) {
            return setResultError("用户名和密码不匹配.");
        }

        String memberToken = TokenUtils.getMemberToken();

        Long userId = loginUser.getId();
        log.info("####用户信息token存放在redis中... key为:{},value:{}", memberToken, userId);
        baseRedisService.setString(memberToken, userId + "", Constants.TOKEN_MEMBER_TIME);
        return setResultSuccess(null, memberToken);
    }

    @Override
    public ResponseBase findUserByToken(String token) {
        if (Objects.isNull(token)) {
            return setResultError("token不能为空");
        }
        String userId = (String) baseRedisService.getString(token);
        if (Objects.isNull(userId)) {
            return setResultError("用户信息不存在");
        }
        UserEntity userEntity = memberDao.findByID(Long.parseLong(userId));
        if (Objects.isNull(userEntity)) {
            return setResultError("用户信息不存在");
        }
        userEntity.setPassword(null);
        return setResultSuccess(userEntity, token);
    }

    @Override
    public ResponseBase findUserByOpenId(String openId) {
        if (Objects.isNull(openId)) {
            return setResultError("openId不能为空");
        }
        UserEntity userEntity = memberDao.findUserByOpenId(openId);
        //沒有找到用戶信息
        if (Objects.isNull(userEntity)) {
            return setResultError(Constants.HTTP_RES_CODE_201, "openid没有关联");
        }
        //找到openId后自动登录
        return setLogin(userEntity);
    }

    @Override
    public ResponseBase qqLogin(UserEntity user) {
        // 1.验证参数
        String openid = user.getOpenid();
        if (StringUtils.isEmpty(openid)) {
            return setResultError("openid不能为空!");
        }
        //根据用户登录
        ResponseBase loginUser = login(user);
        if (!Constants.HTTP_RES_CODE_200.equals(loginUser.getRtnCode())) {
            return loginUser;
        }
        // 4. 获取token信息 根据token从redis获取用户信息
        String token = loginUser.getToken();
        ResponseBase tokenUser = findUserByToken(token);
        if (!Constants.HTTP_RES_CODE_200.equals(tokenUser.getRtnCode())) {
            return tokenUser;
        }
        UserEntity successLoginUser = (UserEntity) tokenUser.getData();

        Long userId = successLoginUser.getId();
        int updateResult = memberDao.updateUserByOpenId(openid, userId);
        if (updateResult <= 0){
            return setResultError("QQ账号关联用户失败!");
        }
        return loginUser;
    }

    private String emailJson(String email) {
        JSONObject rootJson = new JSONObject();
        JSONObject header = new JSONObject();
        header.put("interfaceType", Constants.MSG_EMAIL);
        JSONObject content = new JSONObject();
        content.put("email", email);
        rootJson.put("header", header);
        rootJson.put("content", content);
        return rootJson.toJSONString();
    }

    private void sendMsg(String json) {
        ActiveMQQueue activeMQQueue = new ActiveMQQueue(MESSAGESQUEUE);
        registerMailboxProducer.sendMsg(activeMQQueue, json);
    }

}