package com.blogpress.article.bean.converter;

import com.blogpress.article.bean.dto.ArticleDTO;
import com.blogpress.article.bean.entity.Article;
import com.blogpress.article.bean.response.ArticleVO;
import com.blogpress.article.request.CreateArticleRequest;
import com.blogpress.common.util.bean.BeanCopyUtils;

/**
 * 文章Bean转换器
 * @author JY
 */
public class ArticleBeanConverter {

    public static ArticleDTO toArticleDTO(CreateArticleRequest request) {
        if(request == null){
            return null;
        }
        ArticleDTO dto = new ArticleDTO();
        BeanCopyUtils.copy(request, dto, true);
        return dto;
    }

    public static ArticleDTO toArticleDTO(Article article) {
        if(article == null){
            return null;
        }
        ArticleDTO dto = new ArticleDTO();
        BeanCopyUtils.copy(article, dto);
        return dto;
    }

    public static ArticleVO toArticleVO(Article article) {
        if(article == null){
            return null;
        }
        ArticleVO vo = new ArticleVO();
        BeanCopyUtils.copy(article, vo, true);
        return vo;
    }

    public static ArticleVO toArticleVO(ArticleDTO dto) {
        if(dto == null){
            return null;
        }
        ArticleVO vo = new ArticleVO();
        BeanCopyUtils.copy(dto, vo, true);
        return vo;
    }

}
