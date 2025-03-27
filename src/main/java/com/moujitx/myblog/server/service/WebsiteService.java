package com.moujitx.myblog.server.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.moujitx.myblog.server.entity.Website;
import com.moujitx.myblog.server.mapper.WebsiteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.yulichang.wrapper.MPJLambdaWrapper;

import java.util.List;

/**
 * Function: (Website)表服务接口
 * Author: MOUJITX
 * Date: 2024-08-30 19:56:22
 */

@Service
public class WebsiteService {

    @Autowired
    WebsiteMapper websiteMapper;

    /**
     * 修改数据
     */
    public void update(Website website) {
        websiteMapper.updateById(website);
    }

    /**
     * 查询全部数据
     */
    public List<Website> selectAll() {
        MPJLambdaWrapper<Website> wrapper = new MPJLambdaWrapper<Website>()
                .selectAll(Website.class);
        return websiteMapper.selectList(wrapper);
    }


    /**
     * 多条件查询
     */
    public List<Website> select(Website website) {
        MPJLambdaWrapper<Website> wrapper = new MPJLambdaWrapper<Website>()
                .selectAll(Website.class);

        return websiteMapper.selectList(wrapper);
    }


    /**
     * 唯一值查询
     */
    public Website selectByName(String name) {
        QueryWrapper<Website> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", name);
        return websiteMapper.selectOne(queryWrapper);
    }
}
