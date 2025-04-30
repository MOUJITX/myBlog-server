package com.moujitx.myblog.server.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Function: (User)实体类
 * Author: MOUJITX
 * Date: 2024-07-21 17:13:35
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value = "user")
@Data
public class User extends Public {

    private String username;

    private String password;

    private String nickname;

    @TableField(exist = false)
    private String token;
}
