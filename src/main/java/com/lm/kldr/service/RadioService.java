package com.lm.kldr.service;

import com.lm.kldr.domain.Radio;
import com.lm.kldr.mapper.RadioMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description
 * @author&date Created by louiemain on 2018/1/21 20:45
 */
@Service
public class RadioService {

    @Autowired
    private RadioMapper radioMapper;

    /**
     * @description 新增
     * @author louiemain
     * @date Created on 2018/1/21 20:46
     * @param radio
     * @return java.lang.Integer
     */
    public Integer add(Radio radio) {
        return this.radioMapper.insertRadio(radio);
    }
}
