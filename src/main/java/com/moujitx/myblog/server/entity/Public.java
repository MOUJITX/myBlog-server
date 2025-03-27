package com.moujitx.myblog.server.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.OrderBy;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class Public {

    @TableId(type = IdType.ASSIGN_UUID)
    private String uuid;

    @OrderBy(sort = 9)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date create_time;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date update_time;
}
