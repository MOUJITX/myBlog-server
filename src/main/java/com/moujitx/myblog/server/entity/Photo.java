package com.moujitx.myblog.server.entity;

import cn.hutool.json.JSONArray;
import com.baomidou.mybatisplus.annotation.*;
import com.moujitx.myblog.server.common.JSONArrayObjectTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Function: (Photo)实体类
 * Author: MOUJITX
 * Date: 2024-09-06 22:12:52
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value = "photo")
@Data
public class Photo extends Public {

    private String title;

    private String subtitle;

    @TableField(typeHandler = JSONArrayObjectTypeHandler.class)
    private JSONArray images;

    private String index_img;

    private Boolean is_public;

}
