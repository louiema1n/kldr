package com.lm.kldr.utils;

import java.io.File;

/**
 * @description 文件操作工具类
 * @author&date Created by louiemain on 2018-01-05 13:30
 */
public class FileUtil {

    public void delAllFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            // 存在，获取所有文件
            File[] files = file.listFiles();
            if (files.length == 0) {
                // 空文件夹
                System.out.println("文件夹为空");
                return;
            } else {
                for (File dist : files) {
                    // 遍历所有文件
                    if (dist.isDirectory()) {
                        // 为目录，继续遍历(getAbsolutePath()获取绝对路径)
                        delAllFile(dist.getAbsolutePath());
                    } else {
                        // 为文件
                        dist.delete();
                    }
                }
            }
        } else {
            System.out.println("文件夹不存在");
        }
    }
}
