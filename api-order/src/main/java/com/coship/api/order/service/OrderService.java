package com.coship.api.order.service;

import com.coship.common.base.ResponseBase;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/order")
public interface OrderService {

    @RequestMapping("/updateOrderIdInfo")
    ResponseBase updateOrderIdInfo(@RequestParam("isPay") Integer isPay,
            @RequestParam("payId") String aliPayId,
            @RequestParam("orderNumber") String orderNumber);
}