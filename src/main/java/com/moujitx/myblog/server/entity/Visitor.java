package com.moujitx.myblog.server.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@TableName("visitor")
@Data
public class Visitor{

    @TableId(type = IdType.ASSIGN_UUID)
    private String uuid;

    private  String ipv4;

    private String token;

    private String agent;

    @OrderBy(sort = 9)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date visit_time;
}
