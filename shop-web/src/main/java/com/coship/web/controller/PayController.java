package com.coship.web.controller;

import com.coship.common.base.ResponseBase;
import com.coship.common.constants.Constants;
import com.coship.web.feign.PayCallBackFegin;
import com.coship.web.feign.PayServiceFegin;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
public class PayController {

    private static final String PAY_SUCCESS = "pay_success";
    @Autowired
    private PayServiceFegin payServiceFegin;
    @Autowired
    private PayCallBackFegin payCallBackFegin;

    // 使用token 进行支付
    @RequestMapping("/aliPay")
    public void aliPay(String payToken, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter writer = response.getWriter();
        // 1.参数验证
        if (StringUtils.isEmpty(payToken)) {
            return;
        }
        // 2.调用支付服务接口 获取支付宝html元素
        ResponseBase payTokenResult = payServiceFegin.findPayToken(payToken);
        if (!Constants.HTTP_RES_CODE_200.equals(payTokenResult.getRtnCode())) {
            String msg = payTokenResult.getMsg();
            writer.println(msg);
            return;
        }
        // 3.返回可以执行的html元素给客户端
        LinkedHashMap data = (LinkedHashMap) payTokenResult.getData();
        String payHtml = (String) data.get("payHtml");
        log.info("####PayController###payHtml:{}", payHtml);
        //4. 页面上进行渲染
        writer.println(payHtml);
        writer.close();
    }

    // 同步回调,解决隐藏参数
    @RequestMapping(value = "/callBack/synSuccessPage", method = RequestMethod.POST)
    public String synSuccessPage(HttpServletRequest request, String outTradeNo, String tradeNo,
            String totalAmount) {
        request.setAttribute("outTradeNo", outTradeNo);
        request.setAttribute("tradeNo", tradeNo);
        request.setAttribute("totalAmount", totalAmount);
        return PAY_SUCCESS;
    }

    @RequestMapping(value = "/callBack/hello", method = RequestMethod.GET)
    public String hello(HttpServletRequest request, String outTradeNo, String tradeNo,
            String totalAmount) {
        request.setAttribute("outTradeNo", outTradeNo);
        request.setAttribute("tradeNo", tradeNo);
        request.setAttribute("totalAmount", totalAmount);
        return PAY_SUCCESS;
    }

    @RequestMapping("/callBack/synCallBack")
    public void synCallBack(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        response.setContentType("text/html;charset=utf-8");
        Map<String, String[]> requestParams = request.getParameterMap();
        Map<String, String> params = new HashMap<String, String>();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = iter.next();
            String[] values = requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            // 乱码解决，这段代码在出现乱码时使用
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        PrintWriter writer = response.getWriter();
        ResponseBase synCallBack = payCallBackFegin.synCallBack(params);
        if (!Constants.HTTP_RES_CODE_200.equals(synCallBack.getRtnCode())) {
            return;
        }
        LinkedHashMap data = (LinkedHashMap) synCallBack.getData();
        //成功回调的返回页面
        String htmlFrom = "<form name='punchout_form'"
                + " method='post' action='http://127.0.0.1/callBack/synSuccessPage' >"
                + "<input type='hidden' name='outTradeNo' value='" + data.get("out_trade_no") + "'>"
                + "<input type='hidden' name='tradeNo' value='" + data.get("trade_no") + "'>"
                + "<input type='hidden' name='totalAmount' value='" + data.get("total_amount")
                + "'>"
                + "<input type='submit' value='立即支付' style='display:none'>"
                + "</form><script>document.forms[0].submit();" + "</script>";
        writer.println(htmlFrom);
        writer.close();
    }

    // 异步回调
    @ResponseBody
    @RequestMapping("/callBack/asynCallBack")
    public String asynCallBack(HttpServletRequest request) {
        Map<String, String[]> requestParams = request.getParameterMap();
        Map<String, String> params = new HashMap<String, String>();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = iter.next();
            String[] values = requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            // 乱码解决，这段代码在出现乱码时使用
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        return payCallBackFegin.asynCallBack(params);
    }

}