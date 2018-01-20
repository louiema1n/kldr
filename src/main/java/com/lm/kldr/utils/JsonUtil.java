package com.lm.kldr.utils;

import java.io.*;

/**
 * @description Json相关工具类
 * @author&date Created by louiemain on 2018/1/12 20:22
 */
public class JsonUtil {

    /**
     * @description 读取JsonFile返回json 字符串
     * @author louiemain
     * @date Created on 2018/1/12 20:23
     * @param filePath
     * @return java.lang.String
     */
    public String fromFileJson2String(String filePath) {
        BufferedReader br = null;
        String str = "";
        try {
            InputStreamReader isr = new InputStreamReader(new FileInputStream(new File(filePath)), "UTF-8");
            br = new BufferedReader(isr);

            String tempStr = null;
            while ((tempStr = br.readLine()) != null) {
                str += tempStr;
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        str = str.replace("\r\n", "").replaceAll(" +", "");
        return str;
    }

    /**
     * @description 写入json String到文件
     * @author louiemain
     * @date Created on 2018/1/14 21:09
     * @param filepath
     * @param json
     * @return void
     */
    public void jsonString2File(String filepath, String json) {
//        FileWriter fw = null;
//        PrintWriter pw = null;
        OutputStreamWriter osw = null;
        try {
//            fw = new FileWriter(filepath);
//            pw = new PrintWriter(fw, true);
            osw = new OutputStreamWriter(new FileOutputStream(filepath), "UTF-8");
            osw.write(json);
            osw.flush();
            osw.close();
//            pw.write(json);
//            pw.close();
//            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (osw != null) {
                try {
                    osw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
//            if (pw != null) {
//                pw.close();
//            }
        }
    }
}
