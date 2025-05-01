package com.moujitx.myblog.server.service;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.moujitx.myblog.server.entity.Article;
import com.moujitx.myblog.server.mapper.ArticleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Function: (Article)表服务接口
 * Author: MOUJITX
 * Date: 2024-07-21 01:05:55
 */

@Service
public class ArticleService {

    @Autowired
    ArticleMapper articleMapper;

    /**
     * 新增数据
     */
    public String insert(Article article) {
        article.setUuid("");
        articleMapper.insert(article);
        return article.getUuid();
    }

    /**
     * 修改数据
     */
    public void update(Article article) {
        articleMapper.updateById(article);
    }

    /**
     * 通过主键删除数据
     */
    public void delete(String uuid) {
        articleMapper.deleteById(uuid);
    }

    /**
     * 删除多条数据
     */
    public void batchDelete(List<String> ids) {
        articleMapper.deleteBatchIds(ids);
    }

    /**
     * 查询全部数据
     */
    public List<Article> selectAll() {
        MPJLambdaWrapper<Article> wrapper = new MPJLambdaWrapper<Article>()
                .select(Article::getUuid, Article::getTags, Article::getCreate_time);
        return articleMapper.selectList(wrapper);
    }

    /**
     * 通过ID查询单条数据
     */
    public Article selectById(String uuid) {
        MPJLambdaWrapper<Article> wrapper = new MPJLambdaWrapper<Article>()
                .selectAll(Article.class)
                .eq(Article::getUuid, uuid);
        return articleMapper.selectOne(wrapper);
    }

    /**
     * 多条件查询
     */
    public List<Article> select(Article article) {
        MPJLambdaWrapper<Article> wrapper = new MPJLambdaWrapper<Article>()
                .selectAll(Article.class);

        return articleMapper.selectList(wrapper);
    }

    /**
     * 多条件分页查询
     */
    public Page<Article> selectPage(Article article, Integer pageNum, Integer pageSize) {
        MPJLambdaWrapper<Article> wrapper = new MPJLambdaWrapper<Article>()
                .select(Article::getUuid,
                        Article::getCreate_time,
                        Article::getUpdate_time,
                        Article::getTitle,
                        Article::getAuthor,
                        Article::getCategories,
                        Article::getIs_top,
                        Article::getIs_public,
                        Article::getIs_original,
                        Article::getIs_comment,
                        Article::getIs_link,
                        Article::getIs_private)
                .like(!article.getCategories().isEmpty(), Article::getCategories, article.getCategories().get(0));

        return articleMapper.selectPage(new Page<>(pageNum, pageSize, true), wrapper);
    }

    public Page<Article> page(Article article, Integer pageNum, Integer pageSize) {
        MPJLambdaWrapper<Article> wrapper = new MPJLambdaWrapper<Article>()
                .select(Article::getUuid,
                        Article::getCreate_time,
                        Article::getUpdate_time,
                        Article::getTitle,
                        Article::getDescription,
                        Article::getImage_url,
                        Article::getAuthor,
                        Article::getTags,
                        Article::getCategories,
                        Article::getIs_top,
                        Article::getIs_public,
                        Article::getIs_original,
                        Article::getIs_comment,
                        Article::getIs_link,
                        Article::getIs_private,
                        Article::getSource_url)
                .eq(Article::getIs_public, article.getIs_public())
                .like(!article.getTitle().isEmpty(), Article::getTitle, article.getTitle())
                .like(!article.getCategories().isEmpty(), Article::getCategories, article.getCategories().get(0))
                .like(!article.getTags().isEmpty(), Article::getTags, article.getTags().get(0));

        return articleMapper.selectPage(new Page<>(pageNum, pageSize, true), wrapper);
    }

    public List<Article> selectByCategoryUUID(String uuid) {
        MPJLambdaWrapper<Article> wrapper = new MPJLambdaWrapper<Article>()
                .selectAll(Article.class)
                .like(Article::getCategories, uuid);

        return articleMapper.selectList(wrapper);
    }

    public Article setCategoriesAndTagsName(Article article) {
        if (!article.getTags().isEmpty()) {
            String tagsName = articleMapper.getTagsName(
                    article.getTags().toString()
                            .replace("[", "")
                            .replace("]", "")
                            .replace("\"", ""));
            // System.out.println(tagsName);
            if (StrUtil.isNotBlank(tagsName))
                article.setTags_name(JSONUtil.parseArray("[" + tagsName + "]"));
        }
        if (!article.getCategories().isEmpty()) {
            String categoriesName = articleMapper.getCategoriesName(
                    article.getCategories().toString()
                            .replace("[", "")
                            .replace("]", "")
                            .replace("\"", ""));
            // System.out.println(categoriesName);
            if (StrUtil.isNotBlank(categoriesName))
                article.setCategories_name(JSONUtil.parseArray("[" + categoriesName + "]"));
        }
        return article;
    }

    public Long countByTag(String tagUUID) {
        MPJLambdaWrapper<Article> wrapper = new MPJLambdaWrapper<Article>();
        wrapper.like("tags", tagUUID);
        return articleMapper.selectCount(wrapper);
    }
}
