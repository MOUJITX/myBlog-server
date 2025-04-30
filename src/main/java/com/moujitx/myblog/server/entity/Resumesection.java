package com.moujitx.myblog.server.entity;

import com.baomidou.mybatisplus.annotation.*;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * Function: (Resumesection)实体类
 * Author: MOUJITX
 * Date: 2024-08-11 19:17:04
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value = "resumesection")
@Data
public class Resumesection extends Public {

    private Boolean enabled;

    private String section;

    private String description;

    private Boolean isrow;

    @OrderBy(sort = 1, asc = true)
    private Integer ordernum;

    private Integer min_width;

    private Integer max_width;

    @TableField(exist = false)
    private List<Resume> resumes;
}
