package com.moujitx.myblog.server.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moujitx.myblog.server.entity.Resumesection;
import com.moujitx.myblog.server.mapper.ResumesectionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.yulichang.wrapper.MPJLambdaWrapper;

import java.util.List;

/**
 * Function: (Resumesection)表服务接口
 * Author: MOUJITX
 * Date: 2024-08-11 19:17:04
 */

@Service
public class ResumesectionService {

    @Autowired
    ResumesectionMapper resumesectionMapper;

    /**
     * 新增数据
     */
    public void insert(Resumesection resumesection) {
        resumesection.setUuid("");
        resumesectionMapper.insert(resumesection);
    }

    /**
     * 修改数据
     */
    public void update(Resumesection resumesection) {
        resumesectionMapper.updateById(resumesection);
    }

    /**
     * 通过主键删除数据
     */
    public void delete(String uuid) {
        resumesectionMapper.deleteById(uuid);
    }

    /**
     * 删除多条数据
     */
    public void batchDelete(List<String> ids) {
        resumesectionMapper.deleteBatchIds(ids);
    }

    /**
     * 查询全部数据
     */
    public List<Resumesection> selectAll() {
        MPJLambdaWrapper<Resumesection> wrapper = new MPJLambdaWrapper<Resumesection>()
                .selectAll(Resumesection.class);
        return resumesectionMapper.selectList(wrapper);
    }

    /**
     * 通过ID查询单条数据
     */
    public Resumesection selectById(String uuid) {
        MPJLambdaWrapper<Resumesection> wrapper = new MPJLambdaWrapper<Resumesection>()
                .selectAll(Resumesection.class)
                .eq(Resumesection::getUuid, uuid);
        return resumesectionMapper.selectOne(wrapper);
    }

    /**
     * 多条件查询
     */
    public List<Resumesection> select(Resumesection resumesection) {
        MPJLambdaWrapper<Resumesection> wrapper = new MPJLambdaWrapper<Resumesection>()
                .selectAll(Resumesection.class)
                .eq(Resumesection::getEnabled, resumesection.getEnabled());

        return resumesectionMapper.selectList(wrapper);
    }

    /**
     * 多条件分页查询
     */
    public Page<Resumesection> selectPage(Resumesection resumesection, Integer pageNum, Integer pageSize) {
        MPJLambdaWrapper<Resumesection> wrapper = new MPJLambdaWrapper<Resumesection>()
                .selectAll(Resumesection.class);

        return resumesectionMapper.selectPage(new Page<>(pageNum, pageSize, true), wrapper);
    }
}
