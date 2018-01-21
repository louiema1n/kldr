package com.lm.kldr.service;

import com.lm.kldr.domain.BLCJExam;
import com.lm.kldr.mapper.BLCJExamMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description
 * @author&date Created by louiemain on 2018/1/21 20:45
 */
@Service
public class BLCJExamService {

    @Autowired
    private BLCJExamMapper blcjExamMapper;

    /**
     * @description 新增
     * @author louiemain
     * @date Created on 2018/1/21 20:46
     * @param blcjExam
     * @return java.lang.Integer
     */
    public Integer add(BLCJExam blcjExam) {
        return this.blcjExamMapper.insert(blcjExam);
    }
}
