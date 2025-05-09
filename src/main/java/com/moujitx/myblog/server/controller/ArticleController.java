package com.moujitx.myblog.server.controller;

import cn.hutool.json.JSONArray;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moujitx.myblog.server.common.AuthAccess;
import com.moujitx.myblog.server.common.Result;
import com.moujitx.myblog.server.entity.Article;
import com.moujitx.myblog.server.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Function: (Article)表控制层接口
 * Author: MOUJITX
 * Date: 2024-07-23 21:01:54
 */

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    ArticleService articleService;

    /* 新增数据 */
    @PostMapping("/add")
    public Result add(@RequestBody Article article) {
        list(article);

        String uuid = articleService.insert(article);
        return Result.success("新增数据成功", uuid);
    }

    private void list(@RequestBody Article article) {
        List<String> tags_list = new ArrayList<>();
        for (Object string : article.getTags()) {
            tags_list.add(string.toString());
        }
        Collections.sort(tags_list);
        article.setTags(new JSONArray(tags_list));

        List<String> caategories_list = new ArrayList<>();
        for (Object string : article.getCategories()) {
            caategories_list.add(string.toString());
        }
        Collections.sort(caategories_list);
        article.setCategories(new JSONArray(caategories_list));
    }

    /* 修改数据 */
    @PutMapping("/update")
    public Result update(@RequestBody Article article) {
        list(article);

        articleService.update(article);
        return Result.success("更新数据成功");
    }

    /* 通过主键删除单条数据 */
    @DeleteMapping("/del/{uuid}")
    public Result delete(@PathVariable String uuid) {
        articleService.delete(uuid);
        return Result.success("删除数据成功");
    }

    /* 删除多条数据 */
    @DeleteMapping("/del/batch")
    public Result batchDelete(@RequestBody List<String> ids) {
        if (ids.isEmpty())
            return Result.error("待删除数据为空");
        articleService.batchDelete(ids);
        return Result.success("删除数据成功");
    }

    /* 查询全部数据 */
    @GetMapping("/selectAll")
    public Result selectAll() {
        List<Article> list = articleService.selectAll();
        return Result.success(list);
    }

    /* 通过ID查询单条数据 */
    @GetMapping("/selectById/{uuid}")
    public Result selectById(@PathVariable String uuid) {
        Article article = articleService.selectById(uuid);
        article = articleService.setCategoriesAndTagsName(article);
        return Result.success(article);
    }

    /* 多条件模糊查询 */
    @PostMapping("/select")
    public Result select(@RequestBody Article article) {
        List<Article> list = articleService.select(article);
        return Result.success(list);
    }

    /* 分页模糊多条件 */
    @PostMapping("/pageSelect")
    public Result pageSelect(HttpServletRequest request,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestBody Article article) {
        Page<Article> selectPage = articleService.selectPage(article, pageNum, pageSize);
        Map<String, Object> result = new HashMap<>();
        result.put("list", selectPage.getRecords());
        result.put("total", selectPage.getTotal());
        result.put("totalPage", selectPage.getPages());
        result.put("pageNum", pageNum);
        result.put("pageSize", pageSize);
        return Result.success(result);
    }

    @AuthAccess
    @PostMapping("/page")
    public Result page(@RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestBody Article article) {
        article.setIs_public(true);
        Page<Article> selectPage = articleService.page(article, pageNum, pageSize);
        selectPage.getRecords().forEach(item -> articleService.setCategoriesAndTagsName(item));

        Map<String, Object> result = new HashMap<>();
        result.put("list", selectPage.getRecords());
        result.put("total", selectPage.getTotal());
        result.put("totalPage", selectPage.getPages());
        result.put("pageNum", pageNum);
        result.put("pageSize", pageSize);
        return Result.success(result);
    }

    @AuthAccess
    @GetMapping("/page/{fatherUUID}")
    public Result selectByCategoryUUID(@PathVariable String fatherUUID) {
        List<Article> list = articleService.selectByCategoryUUID(fatherUUID);
        return Result.success(list);
    }

    @AuthAccess
    @GetMapping("/{uuid}")
    public Result articleById(@PathVariable String uuid, @RequestParam(defaultValue = "") String viewCode) {
        Article article = articleService.selectById(uuid);

        if (!article.getIs_private()) {
            article = articleService.setCategoriesAndTagsName(article);
            return Result.success(article);
        }

        if (viewCode.isEmpty()) {
            article.setFull_content("请输入访问口令以查看私密文章");
            article.setView_code(null);
            article = articleService.setCategoriesAndTagsName(article);
            return Result.success(article);
        }

        if (article.getView_code().equals(viewCode)) {
            article.setIs_private(false);
            article.setView_code(null);
            article = articleService.setCategoriesAndTagsName(article);
            return Result.success(article);
        }

        article.setFull_content("访问口令错误");
        article.setView_code(null);
        article = articleService.setCategoriesAndTagsName(article);
        return Result.success(article);
    }

}
