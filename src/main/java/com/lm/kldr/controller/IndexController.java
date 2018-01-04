package com.lm.kldr.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @description 页面控制器
 * @author&date Created by louiemain on 2018-01-04 10:40
 */
@Controller
public class IndexController {

    @RequestMapping("/upload")
    public String upload() {
        return "upload";
    }
}
