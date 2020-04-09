package com.xn.admin.common.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @Auther: zhangjin
 * @Date : 2018/6/24 09:44:25
 * @E-Mail: 15398041134@163.com
 * Description:
 */
public class UploadUtil {

    /**
     * 保存文件
     * @param file  文件流
     * @param filePath  保存文件的路径
     * @param realFileName  原文件名
     * @return  新文件名
     * @throws Exception
     */
    public static String uploadFile(byte[] file, String filePath, String realFileName) throws Exception {
        File targetFile = new File(filePath);
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }

        String[] str = realFileName.split("\\.");
        String suffix = str[str.length - 1].toString().toLowerCase();//获取文件后缀

        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyyMMddHHmmss");
        String nowDate = dateFormat.format(new Date());
        Random r = new Random();
        String fileName = nowDate + r.nextInt(1000) + "." + suffix;//新文件名

        FileOutputStream out = new FileOutputStream(filePath+fileName);
        out.write(file);
        out.flush();
        out.close();
        return fileName;
    }

}
