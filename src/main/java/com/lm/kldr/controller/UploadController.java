package com.lm.kldr.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.lm.kldr.domain.PrjClass;
import com.lm.kldr.domain.ResultSet;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @description 上传控制器
 * @author&date Created by louiemain on 2018-01-04 10:53
 */
@RestController
public class UploadController {

    @Value("${json.read}")
    private String jsonPath;

    @RequestMapping("/upd")
    public void upd(@RequestParam("file") MultipartFile file, @RequestParam("search") String search, HttpServletResponse response) {
        try {
            // 读取Excel文件
            HSSFWorkbook wb = new HSSFWorkbook(file.getInputStream());
            // 读取sheet
            HSSFSheet sheet = wb.getSheetAt(0);
            HSSFWorkbook wbOut = new HSSFWorkbook();
            if (sheet != null) {
                // 遍历行
                HSSFRow row;

                List<PrjClass> prjClasses = json2List(search);
                // 创建输出文件
                createSheet(wbOut, prjClasses, sheet.getRow(2));

                for (int i = 3; i < sheet.getPhysicalNumberOfRows(); i++) {
                    row = sheet.getRow(i);
                    String distVal = row.getCell(6).getStringCellValue();
                    int flag = 0;
                    for (PrjClass p: prjClasses) {
                        String name = p.getName();
                        Map<String, String> data = p.getData();
                        for (String key : data.keySet()) {
                            if (data.get(key).equals(distVal)) {
                                // 获取当前最大行号
                                int numberOfRows = wbOut.getSheet(name).getPhysicalNumberOfRows();
                                HSSFRow toRow = wbOut.getSheet(name).createRow(numberOfRows);
                                copyRow(toRow, row);
                                flag = 1;
                                break;
                            }
                        }
                    }
                    if (flag == 0) {
                        // 放入其他
                        int numberOfRows = wbOut.getSheet("其他").getPhysicalNumberOfRows();
                        HSSFRow toRow = wbOut.getSheet("其他").createRow(numberOfRows);
                        copyRow(toRow, row);
                    }
                }
//                for (int i = 0; i < row.getLastCellNum(); i++) {
//                    HSSFCell cell = row.getCell(i);
////                    System.out.println(cell + "\n");
//                }
            }
            // 下载文件
            String fileName = file.getOriginalFilename() + "-分析后.xls";
//            response.setContentType("application/octet-stream");
//            response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
//            response.flushBuffer();
//            wbOut.write(response.getOutputStream());

            // 下载到指定文件
            File distFile = new File(jsonPath + fileName);
            if (!distFile.getParentFile().exists()) {
                distFile.getParentFile().mkdirs();
            }
            FileOutputStream fos = new FileOutputStream(distFile);
            wbOut.write(fos);
            fos.flush();
            fos.close();

            response.setHeader("content-type", "text/html;charset=utf-8");

            ResultSet<Object> resultSet = new ResultSet<>();
            resultSet.setCode(0);
            resultSet.setCount(1);
            resultSet.setMsg("<a href='" + fileName +"'><i class=\"layui-icon\" >&#xe622;</i> "+fileName+"</a>");
            resultSet.setData(null);

            try {
                PrintWriter pw = response.getWriter();
                String s = JSON.toJSONString(resultSet);
                pw.write(s);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * @description json string转换成List<Object>
     * @author louiemain
     * @date Created on 2018-01-05 10:51
     * @param str
     * @return java.util.List<com.lm.kldr.domain.PrjClass>
     */
    public List<PrjClass> json2List(String str) {
        str = str.replace("\r\n", "").replaceAll(" +", "");

        PrjClass prjClass;

        List<PrjClass> prjClasses = new ArrayList<>();
        JSONArray array = JSON.parseArray(str);
        for (int i = 0; i < array.size(); i++) {
            prjClass = JSON.parseObject(array.get(i).toString(), PrjClass.class);
            prjClasses.add(prjClass);
        }
        return prjClasses;
    }

    /**
     * @description 读取本地Json file并转换成List<Object>
     * @author louiemain
     * @date Created on 2018-01-04 14:55
     * @param
     * @return java.util.List<com.lm.kldr.domain.PrjClass>
     */
    public List<PrjClass> jsonFile2List() {
        BufferedReader br = null;
        String str = "";
        try {
            FileInputStream fis = new FileInputStream(jsonPath + "prjClass.json");
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
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

        PrjClass prjClass;

        List<PrjClass> prjClasses = new ArrayList<>();
        JSONArray array = JSON.parseArray(str);
        for (int i = 0; i < array.size(); i++) {
            prjClass = JSON.parseObject(array.get(i).toString(), PrjClass.class);
            prjClasses.add(prjClass);
        }
        return prjClasses;
    }

    /**
     * @description 根据名称创建sheet
     * @author louiemain
     * @date Created on 2018-01-04 15:34
     * @param wbOut
     * @param prjClasses
     * @return void
     */
    public void createSheet(HSSFWorkbook wbOut, List<PrjClass> prjClasses, HSSFRow firstRow) {
        for (PrjClass p : prjClasses) {
            HSSFSheet sheet = wbOut.createSheet(p.getName());
            // 创建表头
            HSSFRow row = sheet.createRow(0);
            copyRow(row, firstRow);
        }
        HSSFRow otherRow = wbOut.createSheet("其他").createRow(0);
        copyRow(otherRow, firstRow);
    }

    /**
     * @description 复制行数据
     * @author louiemain
     * @date Created on 2018-01-04 15:42
     * @param fromRow
     * @param toRow
     * @return void
     */
    public void copyRow(HSSFRow toRow, HSSFRow fromRow) {
        for (int i = 0; i < fromRow.getLastCellNum(); i++) {
            HSSFCell cell = fromRow.getCell(i);
            // 设置celltype为string
            cell.setCellType(CellType.STRING);
            toRow.createCell(i).setCellValue(cell.getStringCellValue());
        }
    }
}
