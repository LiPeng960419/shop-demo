package com.coship.api.member.service;

import com.coship.api.member.entity.UserEntity;
import com.coship.common.base.ResponseBase;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: lipeng 910138
 * @Date: 2019/9/24 15:53
 */
@RequestMapping("/member")
public interface MemberService {

    // 使用userId查找用户信息
    @RequestMapping(value = "/findUserById", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseBase findUserById(Long userId);

    @RequestMapping(value = "/regUser", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseBase regUser(@RequestBody UserEntity user);

    @RequestMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseBase login(@RequestBody UserEntity user);

}