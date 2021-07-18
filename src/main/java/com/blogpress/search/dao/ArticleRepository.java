package com.blogpress.search.dao;


import com.blogpress.search.bean.entity.SearchArticle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Highlight;
import org.springframework.data.elasticsearch.annotations.HighlightField;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author JY
 */
public interface ArticleRepository extends ElasticsearchRepository<SearchArticle, Long> {

    /**
     * 根据昵称分页查询文章
     * @param pageable Pageable
     * @param titleKeyword 标题关键字
     * @param contentKeyword 正文关键字
     * @return Page<SearchArticle>
     */
    @Highlight(fields = {@HighlightField(name = "nick")})
    Page<SearchArticle> findByTitleContainingOrContentContaining(Pageable pageable, String titleKeyword, String contentKeyword);

    /**
     * 根据文章ID删除用户
     * @param articleId 文章ID
     */
    void deleteByArticleId(Long articleId);
}
