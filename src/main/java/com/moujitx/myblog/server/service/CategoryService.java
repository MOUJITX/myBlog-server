package com.moujitx.myblog.server.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.moujitx.myblog.server.entity.Category;
import com.moujitx.myblog.server.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Function: (Category)表服务接口
 * Author: MOUJITX
 * Date: 2024-07-21 01:05:55
 */

@Service
public class CategoryService {

    @Autowired
    CategoryMapper categoryMapper;

    /**
     * 新增数据
     */
    public void insert(Category category) {
        category.setUuid("");
        categoryMapper.insert(category);
    }

    /**
     * 修改数据
     */
    public void update(Category category) {
        categoryMapper.updateById(category);
    }

    /**
     * 通过主键删除数据
     */
    public void delete(String uuid) {
        categoryMapper.deleteById(uuid);
    }

    /**
     * 删除多条数据
     */
    public void batchDelete(List<String> ids) {
        categoryMapper.deleteBatchIds(ids);
    }

    /**
     * 查询全部数据
     */
    public List<Category> selectAll() {
        MPJLambdaWrapper<Category> wrapper = new MPJLambdaWrapper<Category>()
                .selectAll(Category.class);
        return categoryMapper.selectList(wrapper);
    }

    /**
     * 通过ID查询单条数据
     */
    public Category selectById(String uuid) {
        MPJLambdaWrapper<Category> wrapper = new MPJLambdaWrapper<Category>()
                .selectAll(Category.class)
                .eq(Category::getUuid, uuid);
        return categoryMapper.selectOne(wrapper);
    }

    /**
     * 多条件查询
     */
    public List<Category> select(Category category) {
        MPJLambdaWrapper<Category> wrapper = new MPJLambdaWrapper<Category>()
                .selectAll(Category.class);

        return categoryMapper.selectList(wrapper);
    }

    /**
     * 多条件分页查询
     */
    public Page<Category> selectPage(Category category, Integer pageNum, Integer pageSize, String father_uuid) {
        MPJLambdaWrapper<Category> wrapper = new MPJLambdaWrapper<Category>()
                .selectAll(Category.class)
                .eq(Category::getFather_uuid, father_uuid);

        return categoryMapper.selectPage(new Page<>(pageNum, pageSize, true), wrapper);
    }

    /**
     * 唯一值查询
     */
    public Category selectByCategory(String category) {
        MPJLambdaWrapper<Category> queryWrapper = new MPJLambdaWrapper<>();
        queryWrapper.eq("category", category);
        return categoryMapper.selectOne(queryWrapper);
    }

    public List<Category> selectByFatherUUID(String father_uuid) {
        MPJLambdaWrapper<Category> queryWrapper = new MPJLambdaWrapper<>();
        queryWrapper.selectAll(Category.class).eq("father_uuid", father_uuid);
        return categoryMapper.selectList(queryWrapper);
    }
}
