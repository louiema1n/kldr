package com.lm.kldr.controller;

import com.lm.kldr.utils.JsonUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description
 * @author&date Created by louiemain on 2018-01-20 9:14
 */
@RestController
@RequestMapping("/req")
public class ReqController {

    @RequestMapping(value = "/handle", method = RequestMethod.POST)
    public String handle(String json) {
        new JsonUtil().jsonString2File(System.getProperty("user.dir") + "/json/examCatalog.json", json);
        return "成功";
    }
}
