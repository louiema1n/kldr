package com.lm.kldr.controller;

import com.lm.kldr.utils.FileUtil;
import com.lm.kldr.utils.Req;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

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

    @RequestMapping("/getReq")
    @ResponseBody
    public String getReq() {
        String str = new Req().hcSendGet();
        str = str.replace("/Scripts/jquery-1.8.2.min.js", "https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js");
        str = str.substring(0, str.lastIndexOf("\r\n"));
        str = str.substring(0, str.lastIndexOf("\r\n"));
        str = str.substring(0, str.lastIndexOf("\r\n"));
        str = str.substring(0, str.lastIndexOf("\r\n"));
        str = str.substring(0, str.lastIndexOf("\r\n"));
        str += "\r\n$(function () {\n" +
                "alert($(\"span.ulShow\").text());" +
                "})\n" +
                "</script>\r\n" +
                "</body>\r\n" +
                "</html>\r\n";
        return str;
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
