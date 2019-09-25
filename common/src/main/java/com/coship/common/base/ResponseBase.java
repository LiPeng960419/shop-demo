package com.coship.common.base;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class ResponseBase {

    private Integer rtnCode;
    private String msg;
    private Object data;
    private String token;

    public ResponseBase() {

    }

    public ResponseBase(Integer rtnCode, String msg, Object data, String token) {
        super();
        this.rtnCode = rtnCode;
        this.msg = msg;
        this.data = data;
        this.token = token;
    }

    @Override
    public String toString() {
        return "ResponseBase [rtnCode=" + rtnCode + ", msg=" + msg + ", data=" + data + "]";
    }

}