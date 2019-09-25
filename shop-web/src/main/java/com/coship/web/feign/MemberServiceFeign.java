package com.coship.web.feign;

import com.coship.api.member.service.MemberService;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * @Author: lipeng 910138
 * @Date: 2019/9/25 9:40
 */
@Component
@FeignClient(value = "member")
public interface MemberServiceFeign extends MemberService {

}