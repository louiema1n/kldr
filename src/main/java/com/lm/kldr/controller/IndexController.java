package com.lm.kldr.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lm.kldr.utils.FileUtil;
import com.lm.kldr.utils.JsonUtil;
import com.lm.kldr.utils.Req;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

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

    @RequestMapping("/pdftest")
    public String pdftest() {
        return "pdftest";
    }

    @RequestMapping("/req")
    public String req() {
        return "req";
    }

    @RequestMapping("/test")
    public String test() {
        return "test";
    }

    @RequestMapping("/getTest")
    @ResponseBody
    public List<Map<String, String>> getTest() {

        List<Map<String, String>> list = new LinkedList<>();

        // 从文件获取json
        String examCatalog = new JsonUtil().fromFileJson2String(System.getProperty("user.dir") + "/json/examCatalog.json");
        ObjectMapper mapper = new ObjectMapper();
        String href = "";
        String name = "";
        try {
            JsonNode jsonNode = mapper.readTree(examCatalog);
            for (int i = 0; i < jsonNode.size(); i++) {
                if (i == 0) {
                    JsonNode children1 = jsonNode.get(i).get("children");
                    for (int j = 0; j < children1.size(); j++) {
                        JsonNode children2 = children1.get(j).get("children");
                        for (int k = 0; k < children2.size(); k++) {
                            Map<String, String> map = new LinkedHashMap<>();
                            href = children2.get(k).get("href").toString();
                            name = children2.get(k).get("name").toString();
                            map.put("name", name);
                            map.put("href", href);
                            list.add(map);
                        }

                    }
                } else {
                    JsonNode children1 = jsonNode.get(i).get("children");
                    for (int j = 0; j < children1.size(); j++) {
                        Map<String, String> map = new LinkedHashMap<>();

                        href = children1.get(j).get("href").toString();
                        name = children1.get(j).get("name").toString();
                        map.put("name", name);
                        map.put("href", href);
                        list.add(map);
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
//        return "123";
    }

    @RequestMapping(value = "/getReq/PracticeCenter/MockExam/{url}", method = RequestMethod.GET)
    @ResponseBody
    public String getReq(@PathVariable String url,
                         @RequestParam("TQuestionPlanID") String TQuestionPlanID,
                         @RequestParam("groupName") String groupName,
                         @RequestParam("code") String code,
                         @RequestParam("tb_l_PaperQuePlanID") String tb_l_PaperQuePlanID,
                         @RequestParam("TypeMenuFlag") String TypeMenuFlag,
                         @RequestParam("Model") String Model
    ) {

        String str = new Req().hcSendGet("/PracticeCenter/MockExam/" + url + "?TQuestionPlanID="+TQuestionPlanID
                +"&groupName="+groupName+"&code="+code+"&tb_l_PaperQuePlanID="+tb_l_PaperQuePlanID+"&Model="+Model+"&TypeMenuFlag="+TypeMenuFlag);
//
        str = str.replace("/Scripts/jquery-1.8.2.min.js", "https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js");
        str = str.substring(0, str.lastIndexOf("\r\n"));
        str = str.substring(0, str.lastIndexOf("\r\n"));
        str = str.substring(0, str.lastIndexOf("\r\n"));
        str = str.substring(0, str.lastIndexOf("\r\n"));
        str = str.substring(0, str.lastIndexOf("\r\n"));
        str += "$(function () {\n" +
                "            var catalog = $(\".main_model h1\").text();\n" +
                "            catalog = catalog.replace(/(\\n)|([ ])/g, \"\");\n" +
                "            var types = $(\".nodeItem\");\n" +
                "            var result;\n" +
                "            var topics = new Array();\n" +
                "            for (var i = 0; i < types.length; i++) {\n" +
                "                var item = $(types[i]).children(\".Item.config\");\n" +
                "                var type = $(types[i]).find(\"h2\").text();\n" +
                "                type = type.replace(/(\\n)|([ ])/g, \"\");\n" +
                "                if (i == 0) {\n" +
                "                    for (var j = 0; j < item.length; j++) {\n" +
                "                        var id = $(item[j]).children(\"div.question\").attr(\"id\");\n" +
                "                        var name = $(item[j]).find(\"dt\").text();\n" +
                "                        name = name.replace(/(\\n)|([ ])/g, \"\");\n" +
                "                        result = $(item[j]).find(\"table\").find(\"label\");\n" +
                "                        var radio = '{';\n" +
                "                        for (var k = 0; k < result.length; k++) {\n" +
                "                            var select = $(result[k]).children(\"input\").val();\n" +
                "                            var val = $(result[k]).text();\n" +
                "                            val = val.replace(/(\\n)|([ ])/g, \"\");\n" +
                "                            radio += '\"' + select.toLowerCase() + '\":\"' + val + '\",';\n" +
                "                        }\n" +
                "                        radio = radio.substr(0, radio.length - 1);\n" +
                "                        radio += '}';\n" +
                "                        radio = JSON.parse(radio);\n" +
                "                        var topic = {\n" +
                "                            \"catalog\": catalog,\n" +
                "                            \"type\": type,\n" +
                "                            \"eid\": id,\n" +
                "                            \"commons\": \"\",\n" +
                "                            \"name\": name,\n" +
                "                            \"radio\": radio,\n" +
                "                            \"anser\": \"\",\n" +
                "                            \"analysis\": \"\",\n" +
                "                        };\n" +
                "                        topics.push(topic);\n" +
                "                    }\n" +
                "                }\n" +
                "                else {\n" +
                "                    for (var j = 0; j < item.length; j++) {\n" +
                "                        var commons = $(item[j]).children(\"div.question\").find(\"dt\").html();\n" +
                "                        commons = commons.substr(0, commons.length - 50);\n" +
                "                        var items = $(item[j]).find(\".ContentItem\");\n" +
                "                        for (var l = 0; l < items.length; l++) {\n" +
                "                            var id = $(items[l]).children(\"div.question\").attr(\"id\");\n" +
                "                            var name = $(items[l]).find(\"dt\").text();\n" +
                "                            name = name.replace(/(\\n)|([ ])/g, \"\");\n" +
                "                            result = $(items[l]).find(\"table\").find(\"label\");\n" +
                "                            var radio = '{';\n" +
                "                            for (var k = 0; k < result.length; k++) {\n" +
                "                                var select = $(result[k]).children(\"input\").val();\n" +
                "                                var val = $(result[k]).text();\n" +
                "                                val = val.replace(/(\\n)|([ ])/g, \"\");\n" +
                "                                radio += '\"' + select.toLowerCase() + '\":\"' + val + '\",';\n" +
                "                            }\n" +
                "                            radio = radio.substr(0, radio.length - 1);\n" +
                "                            radio += '}';\n" +
                "                            radio = JSON.parse(radio);\n" +
                "                            var topic = {\n" +
                "                                \"catalog\": catalog,\n" +
                "                                \"type\": type,\n" +
                "                                \"eid\": id,\n" +
                "                                \"commons\": commons,\n" +
                "                                \"name\": name,\n" +
                "                                \"radio\": radio,\n" +
                "                                \"anser\": \"\",\n" +
                "                                \"analysis\": \"\",\n" +
                "                            };\n" +
                "                            topics.push(topic);\n" +
                "                        }\n" +
                "                    }\n" +
                "\n" +
                "                }\n" +
                "            }\n" +
                "\n" +
                "\n" +
                "\n" +
                "           $.ajax({\n" +
                "               url: '/req/handle',\n" +
                "               type: 'post',\n" +
                "               data: {\n" +
                "                   \"json\": JSON.stringify(topics)\n" +
                "               },\n" +
                "               success: function (data) {\n" +
                "                   alert(data);\n" +
                "               }\n" +
                "           });\n" +
                "\n" +
                "        });" +
                "</script>\r\n" +
                "</body>\r\n" +
                "</html>\r\n";
        return str;
//        return "123";
    }

    @RequestMapping("/pdfViewer")
    public String pdfViewer(@RequestParam("file") String file, Model model, HttpServletResponse response) {

        // 使用本地pdf插件预览
        File dist = new File(tempPath + file);
        if (dist.exists()) {
            byte[] bytes = null;
            try {
                FileInputStream fis = new FileInputStream(dist);
                bytes = new byte[fis.available()];
                fis.read(bytes);
                response.getOutputStream().write(bytes);
                fis.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            return null;
        }

        // 使用pdf.js预览
        model.addAttribute("file", "/pdfjs/web/viewer.html?file=" + file);
        return "pdfViewer";
    }
}
