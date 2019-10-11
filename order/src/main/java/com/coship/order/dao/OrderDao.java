package com.coship.order.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface OrderDao {

    @Update("update order_info set isPay=#{isPay} ,payId=#{aliPayId} where orderNumber=#{orderNumber};")
    public int updateOrder(@Param("isPay") Integer isPay, @Param("aliPayId") String aliPayId,
            @Param("orderNumber") String orderNumber);

}