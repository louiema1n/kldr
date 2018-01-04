package com.lm.kldr.domain;

import java.util.Map;

/**
 * @description 项目分类
 * @author&date Created by louiemain on 2018-01-04 13:43
 */
public class PrjClass {

    private String name;
    private Map<String, String> data;

    public PrjClass(String name, Map<String, String> data) {
        this.name = name;
        this.data = data;
    }

    public PrjClass() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }
}
