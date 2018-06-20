package com.yunsunyun.point.vo;

public class MemberPointVo {
    private String integralName;
    private Double integral;

    public String getIntegralName() {
        return integralName;
    }

    public void setIntegralName(String integralName) {
        this.integralName = integralName;
    }

    public Double getIntegral() {
        return integral;
    }

    public void setIntegral(Double integral) {
        this.integral = integral;
    }

    public MemberPointVo() {

    }

    public MemberPointVo(String integralName, Double integral) {
        this.integralName = integralName;
        this.integral = integral;
    }
}
