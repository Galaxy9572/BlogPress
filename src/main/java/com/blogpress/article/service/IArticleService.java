package com.blogpress.article.service;

import com.blogpress.article.bean.response.ArticleVO;
import com.blogpress.article.request.CreateArticleRequest;
import com.github.pagehelper.PageInfo;

/**
 * 文章服务
 * @author JY
 */
public interface IArticleService {

    /**
     * 新建文章
     * @param request CreateArticleRequest
     * @return ArticleVO
     */
    ArticleVO createArticle(CreateArticleRequest request);

    /**
     * 获取文章详情
     * @param articleId 文章ID
     * @return ArticleVO
     */
    ArticleVO getArticleById(Long articleId);

    /**
     * 删除文章
     * @param articleId 文章ID
     * @return 删除成功返回true，否则false
     */
    Boolean deleteArticle(Long articleId);

    /**
     * 根据用户ID分页查询文章
     * @param userId 用户ID
     * @param pageNo 当前页
     * @param pageSize 页大小
     * @return PageInfo
     */
    PageInfo<ArticleVO> listUserArticles(Long userId, Integer pageNo, Integer pageSize);
}
