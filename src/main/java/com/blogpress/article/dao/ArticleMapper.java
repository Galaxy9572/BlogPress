package com.blogpress.article.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blogpress.article.bean.entity.Article;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

/**
 * 文章Mapper
 * @author JY
 */
public interface ArticleMapper extends BaseMapper<Article> {

    /**
     * 根据用户ID分页查询文章
     * @param userId 用户ID
     * @return Page<Article>
     */
    Page<Article> selectUserArticlesByPage(@Param("userId") Long userId);

}
