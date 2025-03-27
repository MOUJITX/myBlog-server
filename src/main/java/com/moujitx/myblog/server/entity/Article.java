package com.moujitx.myblog.server.entity;

import cn.hutool.json.JSONArray;
import com.baomidou.mybatisplus.annotation.*;

import com.moujitx.myblog.server.common.JSONArrayObjectTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Function: (Article)实体类
 * Author: MOUJITX
 * Date: 2024-07-23 20:46:52
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value = "article")
@Data
public class Article extends Public{

    private String title;

    private String description;

    private String image_url;

    private String full_content;

    private String source_url;

    private String author;

    @TableField(typeHandler = JSONArrayObjectTypeHandler.class)
    private JSONArray tags;

    @TableField(typeHandler = JSONArrayObjectTypeHandler.class,exist = false)
    private JSONArray tags_name;

    @TableField(typeHandler = JSONArrayObjectTypeHandler.class)
    private JSONArray categories;

    @TableField(typeHandler = JSONArrayObjectTypeHandler.class,exist = false)
    private JSONArray categories_name;

    @OrderBy(sort = 1)
    private Boolean is_top;

    private Boolean is_original;

    private Boolean is_public;

    private Boolean is_comment;

    private Boolean is_link;

    private Boolean is_private;

    private String view_code;



}

