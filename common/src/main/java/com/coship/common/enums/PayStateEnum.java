package com.coship.common.enums;

/**
 * @Author: lipeng 910138
 * @Date: 2019/10/11 15:54
 */
public enum PayStateEnum {

    WAIT_PAY(0, "待支付"),
    SUCCESS_PAY(1, "支付成功"),
    FAIL_PAY(2, "支付失败");

    private Integer state;
    private String stateName;

    PayStateEnum(Integer state, String stateName) {
        this.state = state;
        this.stateName = stateName;
    }

    public static String getStateByCode(Integer state) {
        for (PayStateEnum pay : PayStateEnum.values()) {
            if (state.equals(pay.getState())) {
                return pay.getStateName();
            }
        }
        return null;
    }


    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }}