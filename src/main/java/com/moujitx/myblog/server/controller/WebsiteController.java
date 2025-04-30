package com.moujitx.myblog.server.controller;

import cn.hutool.json.JSONObject;
import com.moujitx.myblog.server.common.AuthAccess;
import com.moujitx.myblog.server.common.Result;
import com.moujitx.myblog.server.entity.Website;
import com.moujitx.myblog.server.service.WebsiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;

/**
 * Function: (Website)表控制层接口
 * Author: MOUJITX
 * Date: 2024-08-30 19:56:22
 */

@RestController
@RequestMapping("/website")
public class WebsiteController {

    @Autowired
    WebsiteService websiteService;

    /* 修改数据 */
    @PutMapping("/update")
    public Result update(@RequestBody Website website) {
        websiteService.update(website);
        return Result.success("更新数据成功");
    }

    @AuthAccess
    @GetMapping("/")
    public Result load() {
        List<Website> list = websiteService.selectAll();
        Map<String, JSONObject> result = new HashMap<>();
        list.forEach(item -> {
            result.put(item.getName(), item.getValue());
        });
        return Result.success(result);
    }

    /* 查询全部数据 */
    @GetMapping("/selectAll")
    public Result selectAll() {
        List<Website> list = websiteService.selectAll();
        return Result.success(list);
    }

    /* 多条件模糊查询 */
    @PostMapping("/select")
    public Result select(@RequestBody Website website) {
        List<Website> list = websiteService.select(website);
        return Result.success(list);
    }

}
