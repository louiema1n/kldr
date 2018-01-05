package com.lm.kldr.controller;

import com.lm.kldr.utils.FileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @description 页面控制器
 * @author&date Created by louiemain on 2018-01-04 10:40
 */
@Controller
public class IndexController {

    @Value("${file.temp}")
    private String tempPath;

    @RequestMapping("/upload")
    public String upload() {
        // 访问时清空本地temp文件夹
        new FileUtil().delAllFile(tempPath);
        return "upload";
    }
}
