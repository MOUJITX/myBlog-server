package com.moujitx.myblog.server.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * Function: (Category)实体类
 * Author: MOUJITX
 * Date: 2024-07-21 01:05:55
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value = "category")
@Data
public class Category extends Public{

    private String category;

    private String description;

    private String banner_image;

    private String father_uuid;

    @TableField(exist = false)
    private List<Category> children;

    @TableField(exist = false)
    private Boolean hasChildren;

}

