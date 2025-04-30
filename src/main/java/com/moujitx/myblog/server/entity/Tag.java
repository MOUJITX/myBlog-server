package com.moujitx.myblog.server.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Function: (Tag)实体类
 * Author: MOUJITX
 * Date: 2024-07-21 01:05:55
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value = "tag")
@Data
public class Tag extends Public {

    private String tag;

    private String description;

    private String banner_image;

    @TableField(exist = false)
    @OrderBy(sort = 1, asc = false)
    private Long tag_count;

}
