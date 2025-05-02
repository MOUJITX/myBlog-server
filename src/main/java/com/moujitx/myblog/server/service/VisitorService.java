package com.moujitx.myblog.server.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.moujitx.myblog.server.entity.Visitor;
import com.moujitx.myblog.server.mapper.VisitorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class VisitorService {

    @Autowired
    VisitorMapper visitorMapper;

    /**
     * 新增数据
     */
    public void insert(Visitor visitor) {
        visitorMapper.insert(visitor);
    }

    /**
     * 查询全部数据
     */
    public List<Visitor> selectAll() {
        MPJLambdaWrapper<Visitor> wrapper = new MPJLambdaWrapper<Visitor>()
                .selectAll(Visitor.class);
        return visitorMapper.selectList(wrapper);
    }

    /**
     * 通过ID查询单条数据
     */
    public Visitor selectById(String uuid) {
        MPJLambdaWrapper<Visitor> wrapper = new MPJLambdaWrapper<Visitor>()
                .selectAll(Visitor.class)
                .eq(Visitor::getUuid, uuid);
        return visitorMapper.selectOne(wrapper);
    }

    /**
     * 多条件查询
     */
    public List<Visitor> select(Visitor visitor) {
        MPJLambdaWrapper<Visitor> wrapper = new MPJLambdaWrapper<Visitor>()
                .selectAll(Visitor.class);

        return visitorMapper.selectList(wrapper);
    }

    /**
     * 多条件分页查询
     */
    public Page<Visitor> selectPage(Visitor visitor, Integer pageNum, Integer pageSize) {
        MPJLambdaWrapper<Visitor> wrapper = new MPJLambdaWrapper<Visitor>()
                .selectAll(Visitor.class);

        return visitorMapper.selectPage(new Page<>(pageNum, pageSize, true), wrapper);
    }
}
