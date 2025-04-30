package com.moujitx.myblog.server.entity;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.annotation.*;
import com.moujitx.myblog.server.common.JSONObjectTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Function: (Resume)实体类
 * Author: MOUJITX
 * Date: 2024-08-11 20:40:41
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value = "resume")
@Data
public class Resume extends Public {

    private String title;

    private String subtitle;

    private String description;

    @TableField(typeHandler = JSONObjectTypeHandler.class)
    private JSONObject icon;

    private String direction;

    private Integer min_width;
    private Integer max_width;

    @OrderBy(sort = 3, asc = true)
    private Boolean enabled;

    private String section;

    @OrderBy(sort = 4, asc = true)
    private Integer ordernum;

    private Boolean isopen;

    @TableField(exist = false)
    private String section_name;

    @TableField(exist = false)
    @OrderBy(sort = 1, asc = true)
    private Boolean section_enabled;

    @TableField(exist = false)
    @OrderBy(sort = 2, asc = true)
    private Integer section_order;
}
