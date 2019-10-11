package com.coship.api.order.entity;

import java.util.Date;
import lombok.Data;

@Data
public class OrderEntity {

    private Long id;
    /**
     * 用户userid
     */
    private Long userId;
    /**
     * 订单编号商户
     */
    private String orderNumber;
    /**
     * 0 未支付 1已支付
     */
    private Integer isPay;
    /**
     * 支付id 支付宝的内部支付id
     */
    private Long payId;
    private Date created;
    private Date updated;
}
