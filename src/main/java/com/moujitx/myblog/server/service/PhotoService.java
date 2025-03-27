package com.moujitx.myblog.server.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moujitx.myblog.server.entity.Photo;
import com.moujitx.myblog.server.mapper.PhotoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.yulichang.wrapper.MPJLambdaWrapper;

import java.util.List;

/**
 * Function: (Photo)表服务接口
 * Author: MOUJITX
 * Date: 2024-09-06 22:12:52
 */

@Service
public class PhotoService {

    @Autowired
    PhotoMapper photoMapper;

    /**
     * 新增数据
     */
    public void insert(Photo photo) {
        photo.setUuid(null);
        photoMapper.insert(photo);
    }

    /**
     * 修改数据
     */
    public void update(Photo photo) {
        photoMapper.updateById(photo);
    }

    /**
     * 通过主键删除数据
     */
    public void delete(String uuid) {
        photoMapper.deleteById(uuid);
    }

    /**
     * 删除多条数据
     */
    public void batchDelete(List<String> ids) {
        photoMapper.deleteBatchIds(ids);
    }

    /**
     * 查询全部数据
     */
    public List<Photo> selectAll() {
        MPJLambdaWrapper<Photo> wrapper = new MPJLambdaWrapper<Photo>()
                .selectAll(Photo.class);
        return photoMapper.selectList(wrapper);
    }

    /**
     * 通过ID查询单条数据
     */
    public Photo selectById(String uuid) {
        MPJLambdaWrapper<Photo> wrapper = new MPJLambdaWrapper<Photo>()
                .selectAll(Photo.class)
                .eq(Photo::getUuid, uuid);
        return photoMapper.selectOne(wrapper);
    }

    /**
     * 多条件查询
     */
    public List<Photo> select(Photo photo) {
        MPJLambdaWrapper<Photo> wrapper = new MPJLambdaWrapper<Photo>()
                .selectAll(Photo.class);

        return photoMapper.selectList(wrapper);
    }

    /**
     * 多条件分页查询
     */
    public Page<Photo> selectPage(Photo photo, Integer pageNum, Integer pageSize) {
        MPJLambdaWrapper<Photo> wrapper = new MPJLambdaWrapper<Photo>()
                .select(Photo::getUuid, Photo::getTitle, Photo::getIndex_img, Photo::getIs_public,
                        Photo::getCreate_time, Photo::getUpdate_time)
                .eqIfExists(Photo::getIs_public, photo.getIs_public());

        return photoMapper.selectPage(new Page<>(pageNum, pageSize, true), wrapper);
    }

    /**
     * 唯一值查询
     */
    public Photo selectByTitle(String title) {
        QueryWrapper<Photo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title", title);
        return photoMapper.selectOne(queryWrapper);
    }
}
