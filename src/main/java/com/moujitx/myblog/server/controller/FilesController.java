package com.moujitx.myblog.server.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moujitx.myblog.server.common.AuthAccess;
import com.moujitx.myblog.server.common.GlobalSet;
import com.moujitx.myblog.server.common.Result;
import com.moujitx.myblog.server.entity.Files;
import com.moujitx.myblog.server.exception.ServiceException;
import com.moujitx.myblog.server.service.FilesService;
import com.moujitx.myblog.server.utils.OSSUploadUtils;
import com.qiniu.http.Response;
import com.qiniu.storage.model.FetchRet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;

@RestController
@RequestMapping("/files")
public class FilesController {

    @Autowired
    FilesService filesService;

    // 图片上传专用(停用)
    @SuppressWarnings("null")
    @PostMapping("/old/upload/image")
    public Result UploadImg(MultipartFile file, HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        String name = file.getOriginalFilename().concat("");
        String originName = "";
        String endName = "";
        try {
            originName = name.substring(0, name.lastIndexOf("."));
            endName = name.substring(name.lastIndexOf("."));
        } catch (StringIndexOutOfBoundsException e) {
            originName = name;
            endName = "." + file.getContentType().split("/")[1];
        }
        String type = file.getContentType();
        long size = file.getSize();

        assert type != null;
        if (!type.startsWith("image"))
            return Result.error("上传失败，只允许上传图片格式");

        Files files = new Files();
        files.setFile_name(originName);
        files.setOriginal_name(originName);
        files.setEnd_name(endName);
        files.setType(type);
        files.setUser_agent(userAgent);
        files.setSize(size);
        String uuid = filesService.insert(files);

        GlobalSet globalSet = new GlobalSet();
        File folder = new File(globalSet.getUploadPath());
        if (!folder.exists())
            folder.mkdirs();
        try {
            file.transferTo(new File(folder, uuid + endName));
        } catch (Exception e) {
            filesService.delete(uuid);
            return Result.errorWithTitle("上传失败", e.getMessage());
        }

        Map<String, Object> map = new HashMap<>();
        map.put("url", "/api/files/download/" + uuid + endName);
        map.put("name", originName);
        map.put("uuid", uuid);
        return Result.success("文件上传成功", map);
    }

    // 获取URL图片（停用）
    @AuthAccess
    @GetMapping("/old/upload/imageURL")
    public Result UploadImgByURL(@RequestParam String url, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        URL requestURL = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) requestURL.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(10 * 1000);
        InputStream stream = connection.getInputStream();
        int len = 0;
        byte[] test = new byte[1024];

        String name = url.substring(url.lastIndexOf("/") + 1);
        String originName = "";
        String endName = "";
        try {
            originName = name.substring(0, name.lastIndexOf("."));
            endName = name.substring(name.lastIndexOf("."));
        } catch (StringIndexOutOfBoundsException e) {
            originName = name;
            endName = ".jpg";
        }
        Files files = new Files();
        files.setUser_agent(request.getHeader("User-Agent"));
        files.setOriginal_name(originName);
        files.setFile_name(originName);
        files.setEnd_name(endName);
        files.setSource_url(url);
        if (endName.equals(".jpg") || endName.equals(".jpeg"))
            files.setType("image/jpeg");
        else
            files.setType("image/" + endName.replace(".", ""));
        String uuid = filesService.insert(files);

        GlobalSet globalSet = new GlobalSet();
        String realPath = globalSet.getUploadPath();
        File folder = new File(realPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        // 输出流，图片输出的目的文件
        BufferedOutputStream fos = new BufferedOutputStream(new FileOutputStream(realPath + uuid + endName));

        // 以流的方式上传
        while ((len = stream.read(test)) != -1) {
            fos.write(test, 0, len);
        }

        // 记得关闭流，不然消耗资源
        stream.close();
        fos.close();

        Map<String, Object> map = new HashMap<>();
        map.put("url", "/api/files/download/" + uuid + endName);
        map.put("name", uuid);
        return Result.success("文件上传成功", map);
    }

    // 下载文件（暂时弃用，后续可能对接七牛云）
    @AuthAccess
    @GetMapping("/download/{fileName}")
    public void getFiles(@PathVariable String fileName, HttpServletResponse response) {
        OutputStream os; // 新建一个输出流对象
        GlobalSet globalSet = new GlobalSet();
        String filePath = globalSet.getUploadPath() + fileName;
        try {
            if (StrUtil.isNotBlank(fileName)) {
                Files files = filesService.selectById(fileName.split("\\.")[0]);
                byte[] bytes = FileUtil.readBytes(globalSet.getPublicFilePath() + "404.png");
                if (files == null) {
                    response.addHeader("Content-Disposition", "inline;filename=404.png");
                    response.setContentType("image/png");
                } else {
                    response.addHeader("Content-Disposition", "inline;filename="
                            + URLEncoder.encode(files.getFile_name() + files.getEnd_name(), "UTF-8"));
                    response.setContentType(files.getType());
                }
                File file = new File(globalSet.getUploadPath(), fileName);
                if (files != null && file.exists())
                    bytes = FileUtil.readBytes(filePath);
                os = response.getOutputStream(); // 通过输出流返回文件
                os.write(bytes);
                os.flush();
                os.close();
            }
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @PostMapping("/pageSelect")
    public Result pageSelect(HttpServletRequest request,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestBody Files files) {
        Page<Files> selectPage = filesService.selectPage(files, pageNum, pageSize);
        Map<String, Object> result = new HashMap<>();
        result.put("list", selectPage.getRecords());
        result.put("total", selectPage.getTotal());
        result.put("totalPage", selectPage.getPages());
        result.put("pageNum", pageNum);
        result.put("pageSize", pageSize);
        return Result.success(result);
    }

    @PostMapping("/pageSelectImage")
    public Result pageSelectImage(HttpServletRequest request,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestBody Files files) {
        files.setType("image");
        Page<Files> selectPage = filesService.selectPage(files, pageNum, pageSize);
        Map<String, Object> result = new HashMap<>();
        result.put("list", selectPage.getRecords());
        result.put("total", selectPage.getTotal());
        result.put("totalPage", selectPage.getPages());
        result.put("pageNum", pageNum);
        result.put("pageSize", pageSize);
        return Result.success(result);
    }

    /* 通过主键删除单条数据 */
    @DeleteMapping("/del/{uuid}")
    public Result delete(@PathVariable String uuid) {
        String fileKey = uuid + filesService.selectById(uuid).getEnd_name();
        Response response = ossUploadUtils.removeFile(fileKey);
        if (response.statusCode == 200) {
            filesService.delete(uuid);
            return Result.success("删除数据成功");
        } else {
            return Result.error("删除数据失败");
        }
    }

    /* 删除多条数据 */
    @DeleteMapping("/del/batch")
    public Result batchDelete(@RequestBody List<String> ids) {
        List<String> failedIDs = new ArrayList<>();
        for (String id : ids) {
            String fileKey = id + filesService.selectById(id).getEnd_name();
            Response response = ossUploadUtils.removeFile(fileKey);
            if (response.statusCode != 200) {
                failedIDs.add(id);
                ids.remove(id);
            }
        }
        filesService.batchDelete(ids);
        if (failedIDs.isEmpty())
            return Result.success("共[" + ids.size() + "]条数据删除成功");
        else
            return Result.success("共[" + ids.size() + "]条数据删除成功，共[" + failedIDs.size() + "]条数据删除失败");
    }

    /* 七牛云文件上传 */
    @Autowired
    private OSSUploadUtils ossUploadUtils;

    @AuthAccess
    @PostMapping("/oss/upload")
    public Result ossUpload(MultipartFile file, HttpServletRequest request) {
        Map<String, Object> resMap = ossUploadUtils.uploadFile(file);
        if (resMap == null)
            return Result.error("上传失败");

        String filename = (String) resMap.get("filename");
        FetchRet fetchRet = (FetchRet) resMap.get("fetchRet");

        String originName = file.getOriginalFilename();
        String userAgent = request.getHeader("User-Agent");
        String uuid = saveToDB(fetchRet, originName, filename, userAgent, null);

        Map<String, Object> map = new HashMap<>();
        map.put("url", "/files/" + filename);
        map.put("name", originName);
        map.put("uuid", uuid);
        return Result.success("文件上传成功", map);
    }

    @SuppressWarnings("null")
    @AuthAccess
    @PostMapping("/upload/image")
    public Result ossUploadImage(MultipartFile file, HttpServletRequest request) {
        if (!file.getContentType().startsWith("image"))
            return Result.error("上传失败，只允许上传图片格式");

        return ossUpload(file, request);
    }

    /* url图片上传到七牛云 */
    @AuthAccess
    @GetMapping("/upload/imageURL")
    public Result ossFetch(@RequestParam String url, HttpServletRequest request) {
        Map<String, Object> resMap = ossUploadUtils.fetchFile(url);
        if (resMap == null)
            return Result.error("上传失败");

        String filename = (String) resMap.get("filename");
        FetchRet fetchRet = (FetchRet) resMap.get("fetchRet");

        String originName = url.substring(url.lastIndexOf("/") + 1);
        String userAgent = request.getHeader("User-Agent");
        String uuid = saveToDB(fetchRet, originName, filename, userAgent, url);

        Map<String, Object> map = new HashMap<>();
        map.put("url", "/files/" + filename);
        map.put("name", originName);
        map.put("uuid", uuid);
        return Result.success("文件上传成功", map);
    }

    /* 图片数据保存到数据库 */
    public String saveToDB(FetchRet fetchRet,
            String originFileName,
            String filename,
            String userAgent,
            String sourceURL) {
        String uuid = filename.substring(0, filename.lastIndexOf("."));
        String endName = filename.substring(filename.lastIndexOf("."));
        String originName = "";
        if (originFileName == null)
            originName = uuid;
        else if (!originFileName.contains("."))
            originName = originFileName;
        else
            originName = originFileName.substring(0, originFileName.lastIndexOf("."));

        Files files = new Files();
        files.setUuid(uuid);
        files.setFile_name(originName);
        files.setOriginal_name(originName);
        files.setEnd_name(endName);
        files.setType(fetchRet.mimeType);
        files.setSize(fetchRet.fsize);
        files.setUser_agent(userAgent);
        files.setSource_url(sourceURL);
        return filesService.insert(files);
    }

    /* 重命名文件名 */
    @PostMapping("/rename/{uuid}")
    public Result rename(@PathVariable String uuid, @RequestBody Files newName) {
        Files file = filesService.selectById(uuid);
        file.setFile_name(newName.getFile_name());
        filesService.update(file);
        return Result.success("重命名成功");
    }
}
