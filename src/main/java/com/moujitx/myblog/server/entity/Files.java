package com.moujitx.myblog.server.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@TableName("files")
@Data
public class Files extends Public {

    private String file_name;

    private String original_name;

    private String end_name;

    private String type;

    private long size;

    private String user_agent;

    private String source_url;

    @TableField(updateStrategy = FieldStrategy.NEVER, insertStrategy = FieldStrategy.NEVER) // 虚拟列，不对该字段做任何插入/修改操作
    private String download_url;

}
