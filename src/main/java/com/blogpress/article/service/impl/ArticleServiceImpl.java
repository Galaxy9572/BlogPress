package com.blogpress.article.service.impl;

import com.alibaba.fastjson.JSON;
import com.blogpress.article.bean.converter.ArticleBeanConverter;
import com.blogpress.article.bean.dto.ArticleDTO;
import com.blogpress.article.bean.entity.Article;
import com.blogpress.article.bean.response.ArticleVO;
import com.blogpress.article.dao.ArticleMapper;
import com.blogpress.article.service.IArticleService;
import com.blogpress.common.constants.RedisKeyConstants;
import com.blogpress.common.util.AssertUtils;
import com.blogpress.common.util.ContextHolder;
import com.blogpress.common.util.bean.BeanCopyUtils;
import com.blogpress.user.bean.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 文章服务类
 *
 * @author JY
 */
@Slf4j
@Service
public class ArticleServiceImpl implements IArticleService {

    private final ArticleMapper articleMapper;

    private final RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    public ArticleServiceImpl(ArticleMapper articleMapper, RedisTemplate<Object, Object> redisTemplate) {
        this.articleMapper = articleMapper;
        this.redisTemplate = redisTemplate;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ArticleVO createArticle(ArticleDTO articleDTO) {
        UserDTO user = ContextHolder.currentLoginUser();

        Article article = new Article();
        BeanCopyUtils.copy(articleDTO, article);
        article.setUserId(user.getUserId());
        int count = articleMapper.insert(article);

        if (count > 0) {
            Article createdArticle = articleMapper.selectById(article.getArticleId());
            ArticleVO articleVO = ArticleBeanConverter.toArticleVO(createdArticle);
            String articleKey = RedisKeyConstants.articleKey(article.getArticleId());
            redisTemplate.opsForValue().set(articleKey, articleVO);
        } else {
            log.error("Create article failed, param: {}", JSON.toJSONString(articleDTO));
        }
        return null;
    }

    @Override
    public ArticleVO getArticleById(Long articleId) {
        // 从Redis取缓存数据，若有，直接返回
        String articleKey = RedisKeyConstants.articleKey(articleId);
        ArticleVO vo = (ArticleVO) redisTemplate.opsForValue().get(articleKey);
        if (vo != null) {
            return vo;
        }
        // Redis没有数据，从数据库取，并且同步至Redis
        Article article = articleMapper.selectById(articleId);
        AssertUtils.isNotNull(article, "article.not.exist");

        ArticleDTO dto = ArticleBeanConverter.toArticleDTO(article);
        vo = ArticleBeanConverter.toArticleVO(dto);
        redisTemplate.opsForValue().set(articleKey, vo);
        return vo;
    }

    @Override
    public Boolean deleteArticle(Long articleId) {
        UserDTO user = ContextHolder.currentLoginUser();
        Article article = articleMapper.selectById(articleId);
        AssertUtils.isNotNull(article, "article.not.exist");

        AssertUtils.equal(user.getUserId(), article.getUserId(), "no.permission.to.delete.article");

        int count = articleMapper.deleteById(articleId);

        if(count > 0){
            String articleKey = RedisKeyConstants.articleKey(articleId);
            redisTemplate.delete(articleKey);
            return true;
        } else {
            log.error("Delete article failed, articleId: {}", articleId);
            return false;
        }
    }


}
