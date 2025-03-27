package com.moujitx.myblog.server.utils;

import cn.hutool.json.JSONUtil;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.FetchRet;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

/**
 * Function:
 * Author: MOUJITX
 * Date: 2024/8/26 22:32
 */
@Component
public class OSSUploadUtils {

    @Value("${oss.qiniu.accessKey}")
    private String accessKey;

    @Value("${oss.qiniu.accessSecretKey}")
    private String accessSecretKey;

    @Value("${oss.qiniu.bucket}")
    private String bucket;

    @Value("${oss.qiniu.imageUrl}")
    private String url;

    @Value("${oss.qiniu.folder}")
    private String folder;

    private static String returnString = "{\"key\":\"$(key)\",\"mimeType\":\"$(mimeType)\",\"fsize\":$(fsize)}";

    private static Region region = Region.huadong();

    /**
     * 单文件上传
     * 
     * @param multipartFile
     * @return
     */
    @SuppressWarnings("null")
    public Map<String, Object> uploadFile(MultipartFile multipartFile) {
        try {
            // 1、获取文件上传的流
            byte[] fileBytes = multipartFile.getBytes();

            // 2、设置文件名
            String originalFilename = multipartFile.getOriginalFilename();
            String suffix = ".jpg";
            if (originalFilename.contains(".")) {
                suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            String filename = UUID.randomUUID().toString().replace("-", "") + suffix;

            // 3.构造一个带指定 Region 对象的配置类
            Configuration cfg = new Configuration(region);
            UploadManager uploadManager = new UploadManager(cfg);

            // 4.获取七牛云提供的 token
            System.out.println(accessKey);
            Auth auth = Auth.create(accessKey, accessSecretKey);
            StringMap putPolicy = new StringMap();
            putPolicy.put("returnBody", returnString);
            String upToken = auth.uploadToken(bucket, null, 3600, putPolicy);
            Response response = uploadManager.put(fileBytes, folder + filename, upToken);

            // 5.返回上传结果
            Map<String, Object> map = new HashMap<>();
            map.put("filename", filename);
            map.put("fetchRet", JSONUtil.toBean(response.bodyString(), FetchRet.class));
            return map;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map<String, Object> fetchFile(String url) {
        String originName = url.substring(url.lastIndexOf("/") + 1);
        String endName = ".jpg";
        if (originName.contains("."))
            endName = originName.substring(originName.lastIndexOf("."));

        Configuration cfg = new Configuration(region);
        Auth auth = Auth.create(accessKey, accessSecretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        String fileKey = UUID.randomUUID().toString().replace("-", "") + endName;

        try {
            Map<String, Object> map = new HashMap<>();
            map.put("filename", fileKey);
            map.put("fetchRet", bucketManager.fetch(url, bucket, folder + fileKey));
            return map;
        } catch (QiniuException ex) {
            System.err.println(ex.response.toString());
        }
        return null;
    }

    public void renameFile(String oldKey, String newKey) {
        Configuration cfg = new Configuration(region);
        Auth auth = Auth.create(accessKey, accessSecretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.move(bucket, oldKey, bucket, newKey);
        } catch (QiniuException ex) {
            // 如果遇到异常，说明移动失败
            System.err.println(ex.code());
            System.err.println(ex.response.toString());
        }
    }

    public Response removeFile(String key) {
        Configuration cfg = new Configuration(region);
        Auth auth = Auth.create(accessKey, accessSecretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            return bucketManager.delete(bucket, folder + key);
        } catch (QiniuException ex) {
            // 如果遇到异常，说明删除失败
            System.err.println(ex.code());
            System.err.println(ex.response.toString());
        }
        return null;
    }
}
