package com.moujitx.myblog.server.entity;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.annotation.*;
import com.moujitx.myblog.server.common.JSONObjectTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Function: (Website)实体类
 * Author: MOUJITX
 * Date: 2024-08-30 19:56:21
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value = "website")
@Data
public class Website extends Public{

    private String name;

    private String label;

    @TableField(typeHandler = JSONObjectTypeHandler.class)
    private JSONObject value;

    private String description;

    @OrderBy(sort = 1, asc = true)
    private Integer order_num;

}
