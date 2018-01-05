package com.lm.kldr.domain;


import java.util.List;

/**
 * 结果集
 */
public class ResultSet<T> {

    private int code;   // 状态码
    private String msg; // 响应信息
    private int count;  // 记录总数
    List<T> data;      // 记录

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
