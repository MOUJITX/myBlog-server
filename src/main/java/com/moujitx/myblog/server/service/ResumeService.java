package com.moujitx.myblog.server.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moujitx.myblog.server.entity.Resume;
import com.moujitx.myblog.server.entity.Resumesection;
import com.moujitx.myblog.server.mapper.ResumeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.yulichang.wrapper.MPJLambdaWrapper;

import java.util.List;

/**
 * Function: (Resume)表服务接口
 * Author: MOUJITX
 * Date: 2024-08-11 20:43:25
 */

@Service
public class ResumeService {

    @Autowired
    ResumeMapper resumeMapper;

    /**
     * 新增数据
     */
    public void insert(Resume resume) {
        resume.setUuid("");
        resumeMapper.insert(resume);
    }

    /**
     * 修改数据
     */
    public void update(Resume resume) {
        resumeMapper.updateById(resume);
    }

    /**
     * 通过主键删除数据
     */
    public void delete(String uuid) {
        resumeMapper.deleteById(uuid);
    }

    /**
     * 删除多条数据
     */
    public void batchDelete(List<String> ids) {
        resumeMapper.deleteBatchIds(ids);
    }

    /**
     * 查询全部数据
     */
    public List<Resume> selectAll() {
        MPJLambdaWrapper<Resume> wrapper = new MPJLambdaWrapper<Resume>()
                .selectAll(Resume.class)
                .selectAs(Resumesection::getSection, "section_name")
                .selectAs(Resumesection::getEnabled, "section_enabled")
                .selectAs(Resumesection::getOrdernum, "section_order")
                .leftJoin(Resumesection.class, Resumesection::getUuid, Resume::getSection);
        return resumeMapper.selectList(wrapper);
    }

    /**
     * 通过ID查询单条数据
     */
    public Resume selectById(String uuid) {
        MPJLambdaWrapper<Resume> wrapper = new MPJLambdaWrapper<Resume>()
                .selectAll(Resume.class)
                .selectAs(Resumesection::getSection, "section_name")
                .selectAs(Resumesection::getEnabled, "section_enabled")
                .selectAs(Resumesection::getOrdernum, "section_order")
                .leftJoin(Resumesection.class, Resumesection::getUuid, Resume::getSection)
                .eq(Resume::getUuid, uuid);
        return resumeMapper.selectOne(wrapper);
    }

    /**
     * 多条件查询
     */
    public List<Resume> select(Resume resume) {
        MPJLambdaWrapper<Resume> wrapper = new MPJLambdaWrapper<Resume>()
                .selectAll(Resume.class)
                .selectAs(Resumesection::getSection, "section_name")
                .selectAs(Resumesection::getEnabled, "section_enabled")
                .selectAs(Resumesection::getOrdernum, "section_order")
                .leftJoin(Resumesection.class, Resumesection::getUuid, Resume::getSection);

        return resumeMapper.selectList(wrapper);
    }

    /**
     * 多条件分页查询
     */
    public Page<Resume> selectPage(Resume resume, Integer pageNum, Integer pageSize) {
        MPJLambdaWrapper<Resume> wrapper = new MPJLambdaWrapper<Resume>()
                .selectAll(Resume.class)
                .selectAs(Resumesection::getSection, "section_name")
                .selectAs(Resumesection::getEnabled, "section_enabled")
                .selectAs(Resumesection::getOrdernum, "section_order")
                .leftJoin(Resumesection.class, Resumesection::getUuid, Resume::getSection)
                .eq(StrUtil.isNotBlank(resume.getSection()), Resume::getSection, resume.getSection());

        return resumeMapper.selectPage(new Page<>(pageNum, pageSize, true), wrapper);
    }

    public List<Resume> selectBySection(String sectionID) {
        MPJLambdaWrapper<Resume> wrapper = new MPJLambdaWrapper<Resume>()
                .selectAll(Resume.class)
                .leftJoin(Resumesection.class, Resumesection::getUuid, Resume::getSection)
                .eq(Resume::getSection, sectionID)
                .eq(Resume::getEnabled, true);
        return resumeMapper.selectList(wrapper);
    }
}
