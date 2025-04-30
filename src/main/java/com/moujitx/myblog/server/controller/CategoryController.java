package com.moujitx.myblog.server.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moujitx.myblog.server.common.AuthAccess;
import com.moujitx.myblog.server.common.Result;
import com.moujitx.myblog.server.entity.Category;
import com.moujitx.myblog.server.service.ArticleService;
import com.moujitx.myblog.server.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Function: (Category)表控制层接口
 * Author: MOUJITX
 * Date: 2024-07-21 01:05:56
 */

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;
    @Autowired
    ArticleService articleService;

    /* 新增数据 */
    @PostMapping("/add")
    public Result add(@RequestBody Category category) {
        categoryService.insert(category);
        return Result.success("新增数据成功");
    }

    /* 修改数据 */
    @PutMapping("/update")
    public Result update(@RequestBody Category category) {
        categoryService.update(category);
        return Result.success("更新数据成功");
    }

    /* 通过主键删除单条数据 */
    @DeleteMapping("/del/{uuid}")
    public Result delete(@PathVariable String uuid) {
        if (!categoryService.selectByFatherUUID(uuid).isEmpty())
            return Result.errorWithTitle("删除失败", "待删除栏目包含子栏目");
        if (!articleService.selectByCategoryUUID(uuid).isEmpty())
            return Result.errorWithTitle("删除失败", "待删除栏目包含文章");
        categoryService.delete(uuid);
        return Result.success("删除数据成功");
    }

    /* 删除多条数据 */
    @DeleteMapping("/del/batch")
    public Result batchDelete(@RequestBody List<String> ids) {
        if (ids.isEmpty())
            return Result.error("待删除数据为空");
        List<String> hasCategory = new ArrayList<>();
        List<String> hasArticle = new ArrayList<>();
        ids.forEach(id -> {
            if (!categoryService.selectByFatherUUID(id).isEmpty())
                hasCategory.add(categoryService.selectById(id).getCategory());
            else if (!articleService.selectByCategoryUUID(id).isEmpty())
                hasArticle.add(categoryService.selectById(id).getCategory());
            else
                categoryService.delete(id);
        });
        if (hasCategory.isEmpty() && hasArticle.isEmpty())
            return Result.success("删除数据成功");
        else if (!hasCategory.isEmpty() && !hasArticle.isEmpty())
            return Result.errorWithTitle("部分删除失败", "栏目" + hasCategory + "包含子栏目；栏目" + hasArticle + "包含文章");
        else if (!hasCategory.isEmpty())
            return Result.errorWithTitle("部分删除失败", "栏目" + hasCategory + "包含子栏目");
        else
            return Result.errorWithTitle("部分删除失败", "栏目" + hasArticle + "包含文章");
    }

    /* 查询全部数据 */
    @AuthAccess
    @GetMapping("/")
    public Result selectAll() {
        List<Category> rootCategories = categoryService.selectByFatherUUID("root");
        // 递归加载所有层级的子目录
        List<Category> allCategories = new ArrayList<>();
        rootCategories.forEach(rootCategory -> {
            Category loadedRootCategory = loadChildrenRecursively(rootCategory);
            allCategories.add(loadedRootCategory);
        });
        return Result.success(allCategories);
    }

    private Category loadChildrenRecursively(Category category) {
        List<Category> children = categoryService.selectByFatherUUID(category.getUuid());
        if (!children.isEmpty()) {
            children.forEach(child -> child = loadChildrenRecursively(child));
            category.setChildren(children);
            category.setHasChildren(true);
        } else {
            category.setHasChildren(false);
        }
        return category;
    }

    /* 通过ID查询单条数据 */
    @AuthAccess
    @GetMapping("/selectById/{uuid}")
    public Result selectById(@PathVariable String uuid) {
        Category list = categoryService.selectById(uuid);
        return Result.success(list);
    }

    /* 多条件模糊查询 */
    @PostMapping("/select")
    public Result select(@RequestBody Category category) {
        List<Category> list = categoryService.select(category);
        return Result.success(list);
    }

    /* 分页模糊多条件 */
    @PostMapping("/pageSelect")
    public Result pageSelect(HttpServletRequest request,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "root") String father,
            @RequestBody Category category) {
        Page<Category> selectPage = categoryService.selectPage(category, pageNum, pageSize, father);
        Map<String, Object> result = new HashMap<>();
        result.put("list", selectPage.getRecords());
        result.put("total", selectPage.getTotal());
        result.put("totalPage", selectPage.getPages());
        result.put("pageNum", pageNum);
        result.put("pageSize", pageSize);
        return Result.success(result);
    }
}
