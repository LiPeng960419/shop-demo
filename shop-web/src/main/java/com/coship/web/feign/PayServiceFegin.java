package com.coship.web.feign;

import com.coship.api.pay.service.PayService;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;

@Component
@FeignClient("pay")
public interface PayServiceFegin extends PayService {

}