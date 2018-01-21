package com.lm.kldr.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lm.kldr.domain.BLCJExam;
import com.lm.kldr.domain.Radio;
import com.lm.kldr.service.BLCJExamService;
import com.lm.kldr.service.RadioService;
import com.lm.kldr.utils.JsonUtil;
import com.lm.kldr.utils.Req;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @description
 * @author&date Created by louiemain on 2018-01-20 9:14
 */
@RestController
@RequestMapping("/req")
public class ReqController {

    @Autowired
    private RadioService radioService;

    @Autowired
    private BLCJExamService blcjExamService;

    @RequestMapping(value = "/handle", method = RequestMethod.POST)
    public String handle(String json) {

        ObjectMapper mapper = new ObjectMapper();
        String newJson = null;
        try {
            JsonNode jsonNode = mapper.readTree(json);
            for (int i = 0; i < jsonNode.size(); i++) {
                String id = jsonNode.get(i).get("eid").toString();
                id = id.substring(1, id.length() - 1);
                String result = new Req().hcSendPost(id);
                JsonNode resNode = mapper.readTree(result);
                String anser = resNode.get("anser").toString();
                String analysis = resNode.get("analysis").toString();

                BLCJExam blcjExam = mapper.readValue(jsonNode.get(i).toString(), BLCJExam.class);

                this.radioService.add(blcjExam.getRadio());

                blcjExam.setAnser(anser.substring(1, anser.length()-1));
                blcjExam.setAnalysis(analysis.substring(1, analysis.length()-1));
                blcjExam.setRid(blcjExam.getRadio().getId());

                this.blcjExamService.add(blcjExam);

            }
//            newJson = mapper.writeValueAsString(blcjExams);
        } catch (IOException e) {
            e.printStackTrace();
        }

//        new JsonUtil().jsonString2File(System.getProperty("user.dir") + "/json/result.json", newJson);
        return "成功";
    }
}
