package com.moujitx.myblog.server.mapper;

import com.moujitx.myblog.server.entity.Article;
import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.*;

/**
 * Function: (Article)表数据库接口
 * Author: MOUJITX
 * Date: 2024-07-23 20:49:38
 */

@Mapper
public interface ArticleMapper extends MPJBaseMapper<Article> {

        @Select("SELECT GROUP_CONCAT(category_name.category) " +
                        "FROM " +
                        "(SELECT category FROM category " +
                        "WHERE (FIND_IN_SET(uuid, #{categories_uuid})) " +
                        "ORDER BY (FIND_IN_SET(uuid, #{categories_uuid}))) as category_name")
        String getCategoriesName(String categories_uuid);

        @Select("SELECT GROUP_CONCAT(tag_name.tag) " +
                        "FROM " +
                        "(SELECT tag FROM tag " +
                        "WHERE (FIND_IN_SET(uuid, #{tags_uuid})) " +
                        "ORDER BY (FIND_IN_SET(uuid, #{tags_uuid}))) as tag_name")
        String getTagsName(String tags_uuid);
}
