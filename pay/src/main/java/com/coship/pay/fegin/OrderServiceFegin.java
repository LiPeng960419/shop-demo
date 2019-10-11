package com.coship.pay.fegin;

import com.coship.api.order.service.OrderService;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;


@Component
@FeignClient("order")
public interface OrderServiceFegin extends OrderService {

}