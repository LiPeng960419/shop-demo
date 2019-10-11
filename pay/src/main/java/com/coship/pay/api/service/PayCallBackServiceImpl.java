package com.coship.pay.api.service;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.internal.util.AlipaySignature;
import com.coship.api.pay.entity.PaymentInfo;
import com.coship.api.pay.service.PayCallBackService;
import com.coship.common.base.BaseApiService;
import com.coship.common.base.ResponseBase;
import com.coship.common.constants.Constants;
import com.coship.common.enums.OrderStateEnum;
import com.coship.common.enums.PayStateEnum;
import com.coship.pay.config.AlipayConfig;
import com.coship.pay.dao.PaymentInfoDao;
import com.coship.pay.fegin.OrderServiceFegin;
import java.util.Date;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class PayCallBackServiceImpl extends BaseApiService implements PayCallBackService {

    @Autowired
    private PaymentInfoDao paymentInfoDao;

    @Autowired
    private OrderServiceFegin orderServiceFegin;

    // 同步回调
    public ResponseBase synCallBack(@RequestParam Map<String, String> params) {
        // 获取支付宝GET过来反馈信息
        try {
            log.info("####同步回调开始####{}:", params);
            boolean signVerified = AlipaySignature
                    .rsaCheckV1(params, AlipayConfig.alipay_public_key,
                            AlipayConfig.charset, AlipayConfig.sign_type); // 调用SDK验证签名
            // ——请在这里编写您的程序（以下代码仅作参考）——
            if (!signVerified) {
                return setResultError("验签失败!");
            }
            // 商户订单号
            String out_trade_no = params.get("out_trade_no");
            // 支付宝交易号
            String trade_no = params.get("trade_no");
            // 付款金额
            String total_amount = params.get("total_amount");
            JSONObject data = new JSONObject();
            data.put("out_trade_no", out_trade_no);
            data.put("trade_no", trade_no);
            data.put("total_amount", total_amount);
            return setResultSuccess(data, null);
        } catch (Exception e) {
            log.info("######PayCallBackServiceImpl synCallBack##ERROR:#####{}", e);
            return setResultError("系统错误!");
        } finally {
            log.info("####同步回调开始####{}:", params);
        }
    }

    // 异步回调
    public String asynCallBack(@RequestParam Map<String, String> params) {
        // 获取支付宝GET过来反馈信息
        try {
            log.info("####异步回调开始####{}:", params);
            boolean signVerified = AlipaySignature
                    .rsaCheckV1(params, AlipayConfig.alipay_public_key,
                            AlipayConfig.charset, AlipayConfig.sign_type); // 调用SDK验证签名
            // ——请在这里编写您的程序（以下代码仅作参考）——
            if (!signVerified) {
                return Constants.FAIL_STR;
            }
            // 商户订单号
            String outTradeNo = params.get("out_trade_no");
            PaymentInfo paymentInfo = paymentInfoDao.getByOrderIdPayInfo(outTradeNo);
            if (paymentInfo == null) {
                return Constants.FAIL_STR;
            }
            if (PayStateEnum.SUCCESS_PAY.getState().equals(paymentInfo.getState())) {
                return Constants.SUCCESS_STR;
            }
            // 支付宝交易号
            String trade_no = params.get("trade_no");
            // 交易状态
            String trade_status = params.get("trade_status");
            if (trade_status.equals("TRADE_SUCCESS")) {
                paymentInfo.setPayMessage(params.toString());
                paymentInfo.setPlatformorderId(trade_no);
                paymentInfo.setState(PayStateEnum.SUCCESS_PAY.getState());
                paymentInfo.setUpdated(new Date());
                // 手动 begin begin
                paymentInfoDao.updatePayInfo(paymentInfo);
            } else {
                return Constants.FAIL_STR;
            }
            // 调用订单接口通知 支付状态
            ResponseBase orderResult = orderServiceFegin
                    .updateOrderIdInfo(OrderStateEnum.PAID.getState(), trade_no, outTradeNo);
            if (!Constants.HTTP_RES_CODE_200.equals(orderResult.getRtnCode())) {
                // 回滚 手动回滚 rollback
                return Constants.FAIL_STR;
            }
            // 手动 提交 comiit;
            return Constants.SUCCESS_STR;
        } catch (Exception e) {
            log.info("######PayCallBackServiceImpl asynCallBack##ERROR:#####{}", e);
            // 回滚 手动回滚 rollback
            return Constants.FAIL_STR;
        } finally {
            log.info("####异步回调结束####{}:", params);
        }

    }

}