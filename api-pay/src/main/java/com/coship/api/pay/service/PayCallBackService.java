package com.coship.api.pay.service;

import com.coship.common.base.ResponseBase;
import java.util.Map;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: lipeng 910138
 * @Date: 2019/10/11 13:56
 */
@RequestMapping("/callBackService")
public interface PayCallBackService {

    @RequestMapping("/synCallBack")
    public ResponseBase synCallBack(@RequestParam Map<String, String> params);

    /*
    异步调用成功返回success
     */
    @RequestMapping("/asynCallBack")
    public String asynCallBack(@RequestParam Map<String, String> params);

    /*
    测试lcn
     */
    @RequestMapping("/payOrder")
    public String payOrder(@RequestParam("payId") String payId, @RequestParam("temp") int temp);

}