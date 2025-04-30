package com.moujitx.myblog.server.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moujitx.myblog.server.common.AuthAccess;
import com.moujitx.myblog.server.common.Result;
import com.moujitx.myblog.server.entity.Resume;
import com.moujitx.myblog.server.entity.Resumesection;
import com.moujitx.myblog.server.service.ResumeService;
import com.moujitx.myblog.server.service.ResumesectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Function: (Resume)表控制层接口
 * Author: MOUJITX
 * Date: 2024-08-11 20:43:26
 */

@RestController
@RequestMapping("/resume")
public class ResumeController {

    @Autowired
    ResumeService resumeService;
    @Autowired
    ResumesectionService resumesectionService;

    /* 新增数据 */
    @PostMapping("/add")
    public Result add(@RequestBody Resume resume) {
        resumeService.insert(resume);
        return Result.success("新增数据成功");
    }

    /* 修改数据 */
    @PutMapping("/update")
    public Result update(@RequestBody Resume resume) {
        resumeService.update(resume);
        return Result.success("更新数据成功");
    }

    /* 通过主键删除单条数据 */
    @DeleteMapping("/del/{uuid}")
    public Result delete(@PathVariable String uuid) {
        resumeService.delete(uuid);
        return Result.success("删除数据成功");
    }

    /* 删除多条数据 */
    @DeleteMapping("/del/batch")
    public Result batchDelete(@RequestBody List<String> ids) {
        resumeService.batchDelete(ids);
        return Result.success("删除数据成功");
    }

    /* 查询全部数据 */
    @GetMapping("/selectAll")
    public Result selectAll() {
        List<Resume> list = resumeService.selectAll();
        return Result.success(list);
    }

    /* 通过ID查询单条数据 */
    @GetMapping("/selectById/{uuid}")
    public Result selectById(@PathVariable String uuid) {
        Resume list = resumeService.selectById(uuid);
        return Result.success(list);
    }

    /* 多条件模糊查询 */
    @PostMapping("/select")
    public Result select(@RequestBody Resume resume) {
        List<Resume> list = resumeService.select(resume);
        return Result.success(list);
    }

    /* 分页模糊多条件 */
    @PostMapping("/pageSelect")
    public Result pageSelect(HttpServletRequest request,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestBody Resume resume) {
        Page<Resume> selectPage = resumeService.selectPage(resume, pageNum, pageSize);
        Map<String, Object> result = new HashMap<>();
        result.put("list", selectPage.getRecords());
        result.put("total", selectPage.getTotal());
        result.put("totalPage", selectPage.getPages());
        result.put("pageNum", pageNum);
        result.put("pageSize", pageSize);
        return Result.success(result);
    }

    @AuthAccess
    @GetMapping("/")
    public Result selectResume() {
        Resumesection resumesection = new Resumesection();
        resumesection.setEnabled(true);
        List<Resumesection> sections = resumesectionService.select(resumesection);
        sections.forEach(section -> {
            section.setResumes(resumeService.selectBySection(section.getUuid()));
        });
        return Result.success(sections);
    }
}
