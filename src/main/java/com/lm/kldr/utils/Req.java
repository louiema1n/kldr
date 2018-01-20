package com.lm.kldr.utils;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * @description 请求
 * @author&date Created by louiemain on 2018-01-19 13:15
 */
public class Req {

    public void sendPost() {
        PrintWriter pw = null;
        BufferedReader br = null;
        String result = "";
        try {
            URL url = new URL("http://usertk.100xuexi.com/PaperAjax/AjaxAnser");
            // 打开与url之间的连接
            URLConnection conn = url.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");
            // post请求设置
            conn.setDoInput(true);
            conn.setDoOutput(true);
            pw = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            pw.print("QuestionID=9c232004-2bef-4397-b7f6-f37897aa777c&allow=ok");
            pw.flush();
            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            while ((line = br.readLine()) != null) {
                result += line;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(result);
    }

    public String hcSendPost() {

        String result = "";

        // HttpClient
        CloseableHttpClient client = HttpClients.createDefault();
        // 创建post请求
        HttpPost post = new HttpPost("http://usertk.100xuexi.com/PaperAjax/AjaxAnser");
        // 创建参数队列
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("QuestionID", "9c232004-2bef-4397-b7f6-f37897aa777c"));
        params.add(new BasicNameValuePair("allow", "ok"));

        UrlEncodedFormEntity uefEntity = null;
        try {
            uefEntity = new UrlEncodedFormEntity(params, "UTF-8");
            post.setEntity(uefEntity);
            System.out.println("executing request " + post.getURI());
            CloseableHttpResponse response = client.execute(post);
            HttpEntity entity = response.getEntity();

            result = EntityUtils.toString(entity, "UTF-8");
            response.close();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println(result);
        return result;
    }

    public String hcSendGet() {
        CloseableHttpClient client = HttpClients.createDefault();
        String result = "";
        try {
            // 创建参数队列
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("TQuestionPlanID", "2569"));
            params.add(new BasicNameValuePair("groupName", ""));
            params.add(new BasicNameValuePair("code", ""));
            params.add(new BasicNameValuePair("tb_l_PaperQuePlanID", "61463"));
            params.add(new BasicNameValuePair("Model", "chapter"));
            params.add(new BasicNameValuePair("TypeMenuFlag", "2"));

            UrlEncodedFormEntity uefEntity = null;
            uefEntity = new UrlEncodedFormEntity(params, "UTF-8");
            String param = EntityUtils.toString(uefEntity);
            HttpGet httpGet = new HttpGet("http://usertk.100xuexi.com/PracticeCenter/Chapter/Index" + "?" + param);
            System.out.println("executing request " + httpGet.getURI());
            CloseableHttpResponse response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();
            // EntityUtils.toString一个方法内只能执行1次
            result = EntityUtils.toString(entity, "UTF-8");
            response.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
