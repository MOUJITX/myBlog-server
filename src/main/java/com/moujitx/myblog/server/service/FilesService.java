package com.moujitx.myblog.server.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.moujitx.myblog.server.entity.Files;
import com.moujitx.myblog.server.mapper.FilesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Function: (File)表服务接口
 * Author: MOUJITX
 * Date: 2024-07-21 01:05:55
 */

@Service
public class FilesService {

    @Autowired
    FilesMapper filesMapper;

    /**
     * 新增数据
     */
    public String insert(Files files) {
        filesMapper.insert(files);
        return files.getUuid();
    }

    /**
     * 修改数据
     */
    public void update(Files files) {
        filesMapper.updateById(files);
    }

    /**
     * 通过主键删除数据
     */
    public void delete(String uuid) {
        filesMapper.deleteById(uuid);
    }

    /**
     * 删除多条数据
     */
    public void batchDelete(List<String> ids) {
        filesMapper.deleteBatchIds(ids);
    }

    /**
     * 查询全部数据
     */
    public List<Files> selectAll() {
        MPJLambdaWrapper<Files> wrapper = new MPJLambdaWrapper<Files>()
                .selectAll(Files.class);
        return filesMapper.selectList(wrapper);
    }

    /**
     * 通过ID查询单条数据
     */
    public Files selectById(String uuid) {
        MPJLambdaWrapper<Files> wrapper = new MPJLambdaWrapper<Files>()
                .selectAll(Files.class)
                .eq(Files::getUuid, uuid);
        return filesMapper.selectOne(wrapper);
    }

    /**
     * 多条件查询
     */
    public List<Files> select(Files files) {
        MPJLambdaWrapper<Files> wrapper = new MPJLambdaWrapper<Files>()
                .selectAll(Files.class);

        return filesMapper.selectList(wrapper);
    }

    /**
     * 多条件分页查询
     */
    public Page<Files> selectPage(Files files, Integer pageNum, Integer pageSize) {
        MPJLambdaWrapper<Files> wrapper = new MPJLambdaWrapper<Files>()
                .selectAll(Files.class)
                .likeIfExists(Files::getFile_name, files.getFile_name())
                .likeRightIfExists(Files::getType, files.getType());

        return filesMapper.selectPage(new Page<>(pageNum, pageSize, true), wrapper);
    }

    /**
     * 唯一值查询
     */
    public Files selectByFilename(String filename) {
        MPJLambdaWrapper<Files> queryWrapper = new MPJLambdaWrapper<>();
        queryWrapper.eq("file_name", filename);
        return filesMapper.selectOne(queryWrapper);
    }
}
