package com.coship.common.base;

import com.coship.common.constants.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BaseApiService {

    @Autowired
    protected BaseRedisService baseRedisService;

    // 返回错误，可以传msg
    public ResponseBase setResultError(String msg) {
        return setResult(Constants.HTTP_RES_CODE_500, msg, null, null);
    }

    public ResponseBase setResultError(Integer code, String msg) {
        return setResult(code, msg, null, null);
    }

    public ResponseBase setResultError() {
        return setResult(Constants.HTTP_RES_CODE_500, Constants.HTTP_RES_CODE_500_VALUE, null,
                null);
    }

    // 返回成功，可以传data值
    public ResponseBase setResultSuccess(Object data, String token) {
        return setResult(Constants.HTTP_RES_CODE_200, Constants.HTTP_RES_CODE_200_VALUE, data,
                token);
    }

    // 返回成功，沒有data值
    public ResponseBase setResultSuccess() {
        return setResult(Constants.HTTP_RES_CODE_200, Constants.HTTP_RES_CODE_200_VALUE, null,
                null);
    }

    // 返回成功，沒有data值
    public ResponseBase setResultSuccess(String msg, String token) {
        return setResult(Constants.HTTP_RES_CODE_200, msg, null, token);
    }

    // 通用封装
    public ResponseBase setResult(Integer code, String msg, Object data, String token) {
        return new ResponseBase(code, msg, data, token);
    }

}