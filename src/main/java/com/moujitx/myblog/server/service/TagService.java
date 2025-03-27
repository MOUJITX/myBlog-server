package com.moujitx.myblog.server.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moujitx.myblog.server.entity.Tag;
import com.moujitx.myblog.server.mapper.TagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.yulichang.wrapper.MPJLambdaWrapper;

import java.util.List;

/**
 * Function: (Tag)表服务接口
 * Author: MOUJITX
 * Date: 2024-07-21 01:05:55
 */

@Service
public class TagService {

    @Autowired
    TagMapper tagMapper;

    /**
     * 新增数据
     */
    public void insert(Tag tag) {
        tag.setUuid("");
        tagMapper.insert(tag);
    }

    /**
     * 修改数据
     */
    public void update(Tag tag) {
        tagMapper.updateById(tag);
    }

    /**
     * 通过主键删除数据
     */
    public void delete(String uuid) {
        tagMapper.deleteById(uuid);
    }

    /**
     * 删除多条数据
     */
    public void batchDelete(List<String> ids) {
        tagMapper.deleteBatchIds(ids);
    }

    /**
     * 查询全部数据
     */
    public List<Tag> selectAll() {
        MPJLambdaWrapper<Tag> wrapper = new MPJLambdaWrapper<Tag>()
                .selectAll(Tag.class);
        return tagMapper.selectList(wrapper);
    }

    /**
     * 通过ID查询单条数据
     */
    public Tag selectById(String uuid) {
        MPJLambdaWrapper<Tag> wrapper = new MPJLambdaWrapper<Tag>()
                .selectAll(Tag.class)
                .eq(Tag::getUuid, uuid);
        return tagMapper.selectOne(wrapper);
    }

    /**
     * 多条件查询
     */
    public List<Tag> select(Tag tag) {
        MPJLambdaWrapper<Tag> wrapper = new MPJLambdaWrapper<Tag>()
                .selectAll(Tag.class);

        return tagMapper.selectList(wrapper);
    }

    /**
     * 多条件分页查询
     */
    public Page<Tag> selectPage(Tag tag, Integer pageNum, Integer pageSize) {
        MPJLambdaWrapper<Tag> wrapper = new MPJLambdaWrapper<Tag>()
                .selectAll(Tag.class);

        return tagMapper.selectPage(new Page<>(pageNum, pageSize, true), wrapper);
    }

    /**
     * 唯一值查询
     */
    public Tag selectByTag(String tag) {
        MPJLambdaWrapper<Tag> queryWrapper = new MPJLambdaWrapper<>();
        queryWrapper.eq("tag", tag);
        return tagMapper.selectOne(queryWrapper);
    }
}
