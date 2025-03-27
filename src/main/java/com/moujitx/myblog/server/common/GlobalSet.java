package com.moujitx.myblog.server.common;

import lombok.Getter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.system.ApplicationHome;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Function:
 * Author: MOUJITX
 * Date: 2023/11/9 13:22
 */

public class GlobalSet {

    //服务器地址
    @Getter
    private final String hostURL = "http://127.0.0.1:8080/";

    ApplicationHome ah = new ApplicationHome(SpringBootApplication.class);  //获取jar存储位置

    @Getter
    //private String docStorePath = ah.getSource().getParentFile().toString();  //获取相对于jar存储位置的上级目录（服务器部署后）
    private final String docStorePath = System.getProperty("user.dir");   //获取文件存储目录（本地测试）


    @Getter
    private final String exportName = docStorePath + "/files/export";

    @Getter
    private final String publicFilePath = docStorePath + "/files/public/";

    @Getter
    private final String uploadPath = docStorePath + "/files/upload/";

    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    String formatDate = sdf.format(new Date());
    String UniCodeName = UUID.randomUUID().toString();
    @Getter
    private final String endName = "_" + formatDate + "_" + UniCodeName + ".xls";
}
