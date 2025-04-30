package com.moujitx.myblog.server.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moujitx.myblog.server.common.AuthAccess;
import com.moujitx.myblog.server.common.Result;
import com.moujitx.myblog.server.entity.Photo;
import com.moujitx.myblog.server.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Function: (Photo)表控制层接口
 * Author: MOUJITX
 * Date: 2024-09-06 22:12:52
 */

@RestController
@RequestMapping("/photo")
public class PhotoController {

    @Autowired
    PhotoService photoService;

    /* 新增数据 */
    @PostMapping("/add")
    public Result add(@RequestBody Photo photo) {
        photoService.insert(photo);
        return Result.success("新增数据成功");
    }

    /* 修改数据 */
    @PutMapping("/update")
    public Result update(@RequestBody Photo photo) {
        photoService.update(photo);
        return Result.success("更新数据成功");
    }

    /* 通过主键删除单条数据 */
    @DeleteMapping("/del/{uuid}")
    public Result delete(@PathVariable String uuid) {
        photoService.delete(uuid);
        return Result.success("删除数据成功");
    }

    /* 删除多条数据 */
    @DeleteMapping("/del/batch")
    public Result batchDelete(@RequestBody List<String> ids) {
        photoService.batchDelete(ids);
        return Result.success("删除数据成功");
    }

    /* 查询全部数据 */
    @GetMapping("/selectAll")
    public Result selectAll() {
        List<Photo> list = photoService.selectAll();
        return Result.success(list);
    }

    /* 通过ID查询单条数据 */
    @AuthAccess
    @GetMapping("/selectById/{uuid}")
    public Result selectById(@PathVariable String uuid) {
        Photo list = photoService.selectById(uuid);
        return Result.success(list);
    }

    /* 多条件模糊查询 */
    @PostMapping("/select")
    public Result select(@RequestBody Photo photo) {
        List<Photo> list = photoService.select(photo);
        return Result.success(list);
    }

    /* 分页模糊多条件 */
    @PostMapping("/pageSelect")
    public Result pageSelect(HttpServletRequest request,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestBody Photo photo) {
        Page<Photo> selectPage = photoService.selectPage(photo, pageNum, pageSize);
        Map<String, Object> result = new HashMap<>();
        result.put("list", selectPage.getRecords());
        result.put("total", selectPage.getTotal());
        result.put("totalPage", selectPage.getPages());
        result.put("pageNum", pageNum);
        result.put("pageSize", pageSize);
        return Result.success(result);
    }

    @AuthAccess
    @PostMapping("/list")
    public Result pageList(HttpServletRequest request,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestBody Photo photo) {
        photo.setIs_public(true);
        Page<Photo> selectPage = photoService.selectPage(photo, pageNum, pageSize);
        Map<String, Object> result = new HashMap<>();
        result.put("list", selectPage.getRecords());
        result.put("total", selectPage.getTotal());
        result.put("totalPage", selectPage.getPages());
        result.put("pageNum", pageNum);
        result.put("pageSize", pageSize);
        return Result.success(result);
    }
}
