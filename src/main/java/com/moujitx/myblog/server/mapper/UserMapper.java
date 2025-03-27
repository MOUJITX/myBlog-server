package com.moujitx.myblog.server.mapper;

import com.moujitx.myblog.server.entity.User;
import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.*;

/**
 * Function: (User)表数据库接口
 * Author: MOUJITX
 * Date: 2024-07-21 17:13:36
 */

@Mapper
public interface UserMapper extends MPJBaseMapper<User> {

}
