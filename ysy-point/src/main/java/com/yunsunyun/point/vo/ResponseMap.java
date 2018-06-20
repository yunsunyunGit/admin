package com.yunsunyun.point.vo;

public class ResponseMap {

    private Integer code;

    private Integer totalSize;

    private String msg;

    private Object data;

    public ResponseMap() {
    }

    public ResponseMap(Integer code) {
        this.code = code;
    }

    public ResponseMap(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Integer getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(Integer totalSize) {
        this.totalSize = totalSize;
    }

}
