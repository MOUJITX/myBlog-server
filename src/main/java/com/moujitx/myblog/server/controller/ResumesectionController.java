package com.moujitx.myblog.server.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moujitx.myblog.server.common.Result;
import com.moujitx.myblog.server.entity.Resumesection;
import com.moujitx.myblog.server.service.ResumesectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Function: (Resumesection)表控制层接口
 * Author: MOUJITX
 * Date: 2024-08-11 19:17:04
 */

@RestController
@RequestMapping("/resumeSection")
public class ResumesectionController {

    @Autowired
    ResumesectionService resumesectionService;

    /*新增数据*/
    @PostMapping("/add")
    public Result add(@RequestBody Resumesection resumesection) {
        resumesectionService.insert(resumesection);
        return Result.success("新增数据成功");
    }


    /*修改数据*/
    @PutMapping("/update")
    public Result update(@RequestBody Resumesection resumesection) {
        resumesectionService.update(resumesection);
        return Result.success("更新数据成功");
    }

    /*通过主键删除单条数据*/
    @DeleteMapping("/del/{uuid}")
    public Result delete(@PathVariable String uuid) {
        resumesectionService.delete(uuid);
        return Result.success("删除数据成功");
    }

    /*删除多条数据*/
    @DeleteMapping("/del/batch")
    public Result batchDelete(@RequestBody List<String> ids) {
        if (ids.isEmpty()) return Result.error("待删除数据为空");
        resumesectionService.batchDelete(ids);
        return Result.success("删除数据成功");
    }

    /*查询全部数据*/
    @GetMapping("/selectAll")
    public Result selectAll() {
        List<Resumesection> list = resumesectionService.selectAll();
        return Result.success(list);
    }

    /*通过ID查询单条数据*/
    @GetMapping("/selectById/{uuid}")
    public Result selectById(@PathVariable String uuid) {
        Resumesection list = resumesectionService.selectById(uuid);
        return Result.success(list);
    }

    /*多条件模糊查询*/
    @PostMapping("/select")
    public Result select(@RequestBody Resumesection resumesection) {
        List<Resumesection> list = resumesectionService.select(resumesection);
        return Result.success(list);
    }

    /*分页模糊多条件*/
    @PostMapping("/pageSelect")
    public Result pageSelect(HttpServletRequest request,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             @RequestBody Resumesection resumesection) {
        Page<Resumesection> selectPage = resumesectionService.selectPage(resumesection, pageNum, pageSize);
        Map<String, Object> result = new HashMap<>();
        result.put("list", selectPage.getRecords());
        result.put("total", selectPage.getTotal());
        result.put("totalPage", selectPage.getPages());
        result.put("pageNum", pageNum);
        result.put("pageSize", pageSize);
        return Result.success(result);
    }
}
