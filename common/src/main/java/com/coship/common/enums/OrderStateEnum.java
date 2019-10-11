package com.coship.common.enums;

/**
 * @Author: lipeng 910138
 * @Date: 2019/10/11 18:29
 */
public enum OrderStateEnum {

    PAID(1, "已支付"),
    NOT_PAID(0, "未支付");

    private Integer state;
    private String stateName;

    OrderStateEnum(Integer state, String stateName) {
        this.state = state;
        this.stateName = stateName;
    }

    public static String getStateByCode(Integer state) {
        for (OrderStateEnum pay : OrderStateEnum.values()) {
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
    }

}
