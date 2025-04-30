package com.moujitx.myblog.server.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moujitx.myblog.server.common.AuthAccess;
import com.moujitx.myblog.server.common.Result;
import com.moujitx.myblog.server.entity.Tag;
import com.moujitx.myblog.server.service.ArticleService;
import com.moujitx.myblog.server.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Function: (Tag)表控制层接口
 * Author: MOUJITX
 * Date: 2024-07-21 01:05:56
 */

@RestController
@RequestMapping("/tag")
public class TagController {

    @Autowired
    TagService tagService;

    /* 新增数据 */
    @PostMapping("/add")
    public Result add(@RequestBody Tag tag) {
        if (tagService.selectByTag(tag.getTag()) != null)
            return Result.error("该标签已存在");

        tagService.insert(tag);
        return Result.success("新增数据成功");
    }

    /* 修改数据 */
    @PutMapping("/update")
    public Result update(@RequestBody Tag tag) {
        Tag tagConfirm = tagService.selectByTag(tag.getTag());
        if (tagConfirm != null && !tagConfirm.getUuid().equals(tag.getUuid()))
            return Result.error("该标签已存在");

        tagService.update(tag);
        return Result.success("更新数据成功");
    }

    /* 通过主键删除单条数据 */
    @DeleteMapping("/del/{uuid}")
    public Result delete(@PathVariable String uuid) {
        tagService.delete(uuid);
        return Result.success("删除数据成功");
    }

    /* 删除多条数据 */
    @DeleteMapping("/del/batch")
    public Result batchDelete(@RequestBody List<String> ids) {
        if (ids.isEmpty())
            return Result.error("待删除数据为空");
        tagService.batchDelete(ids);
        return Result.success("删除数据成功");
    }

    /* 查询全部数据 */
    @GetMapping("/selectAll")
    public Result selectAll() {
        List<Tag> list = tagService.selectAll();
        return Result.success(list);
    }

    @Autowired
    ArticleService articleService;

    @AuthAccess
    @GetMapping("/")
    public Result countAll() {
        List<Tag> list = tagService.selectAll();
        list.forEach(item -> {
            item.setTag_count(articleService.countByTag(item.getUuid()));
        });
        return Result.success(list);
    }

    /* 通过ID查询单条数据 */
    @AuthAccess
    @GetMapping("/selectById/{uuid}")
    public Result selectById(@PathVariable String uuid) {
        Tag list = tagService.selectById(uuid);
        return Result.success(list);
    }

    /* 多条件模糊查询 */
    @PostMapping("/select")
    public Result select(@RequestBody Tag tag) {
        List<Tag> list = tagService.select(tag);
        return Result.success(list);
    }

    /* 分页模糊多条件 */
    @PostMapping("/pageSelect")
    public Result pageSelect(HttpServletRequest request,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestBody Tag tag) {
        Page<Tag> selectPage = tagService.selectPage(tag, pageNum, pageSize);
        Map<String, Object> result = new HashMap<>();
        result.put("list", selectPage.getRecords());
        result.put("total", selectPage.getTotal());
        result.put("totalPage", selectPage.getPages());
        result.put("pageNum", pageNum);
        result.put("pageSize", pageSize);
        return Result.success(result);
    }
}
