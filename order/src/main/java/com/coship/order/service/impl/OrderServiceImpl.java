package com.coship.order.service.impl;

import com.coship.api.order.service.OrderService;
import com.coship.common.base.BaseApiService;
import com.coship.common.base.ResponseBase;
import com.coship.order.dao.OrderDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
public class OrderServiceImpl extends BaseApiService implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Override
    public ResponseBase updateOrderIdInfo(@RequestParam("isPay") Integer isPay,
            @RequestParam("payId") String aliPayId,
            @RequestParam("orderNumber") String orderNumber) {
        int updateOrder = 0;
        try {
            updateOrder = orderDao.updateOrder(isPay, aliPayId, orderNumber);
            if (updateOrder <= 0) {
                log.error("更新订单信息错误!");
                return setResultError("更新订单信息错误!");
            }
        } catch (Exception e) {
            log.error("更新订单信息错误!", e);
            return setResultError("更新订单信息错误!");
        }
        return setResultSuccess();
    }

}