package com.blogpress.article.service.impl;

import com.alibaba.fastjson.JSON;
import com.blogpress.article.bean.converter.ArticleBeanConverter;
import com.blogpress.article.bean.dto.ArticleDTO;
import com.blogpress.article.bean.entity.Article;
import com.blogpress.article.bean.response.ArticleVO;
import com.blogpress.article.dao.ArticleMapper;
import com.blogpress.article.service.IArticleService;
import com.blogpress.common.constants.RedisKeyConstants;
import com.blogpress.common.exception.BusinessException;
import com.blogpress.common.util.AssertUtils;
import com.blogpress.common.util.ContextHolder;
import com.blogpress.common.util.PageUtils;
import com.blogpress.common.util.bean.BeanCopyUtils;
import com.blogpress.count.bean.entity.Count;
import com.blogpress.count.dao.CountMapper;
import com.blogpress.count.enums.ContentTypeEnum;
import com.blogpress.user.bean.dto.UserDTO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 文章服务类
 *
 * @author JY
 */
@Slf4j
@Service
public class ArticleServiceImpl implements IArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private CountMapper countMapper;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ArticleVO createArticle(ArticleDTO articleDTO) {
        UserDTO user = ContextHolder.currentLoginUser();

        Article article = new Article();
        BeanCopyUtils.copy(articleDTO, article);
        article.setUserId(user.getUserId());
        int articleInsertCount = articleMapper.insert(article);

        Count count = new Count();
        count.setContentId(article.getArticleId());
        count.setContentType(ContentTypeEnum.ARTICLE.getCode());
        int countCount = countMapper.insert(count);

        if (articleInsertCount > 0 && countCount > 0) {
            // 插入文章主体
            Article createdArticle = articleMapper.selectById(article.getArticleId());
            Count articleCount = countMapper.selectById(count.getCountId());
            ArticleVO articleVO = ArticleBeanConverter.toArticleVO(createdArticle, articleCount);
            String articleKey = RedisKeyConstants.articleKey(article.getArticleId());
            redisTemplate.opsForValue().set(articleKey, articleVO);
            return articleVO;
        } else {
            log.error("Create article failed, param: {}", JSON.toJSONString(articleDTO));
            throw BusinessException.of("operate.failed");
        }
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
            throw BusinessException.of("operate.failed");
        }
    }

    @Override
    public PageInfo<ArticleVO> listUserArticles(Long userId, Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        Page<Article> articleList = articleMapper.selectUserArticlesByPage(userId);
        PageInfo<ArticleVO> pageInfo = PageUtils.of(articleList, ArticleVO.class);
        if(!CollectionUtils.isEmpty(articleList)){
            List<Long> articleIds = articleList.stream().map(Article::getArticleId).collect(Collectors.toList());
            Map<Long, Count> countMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(articleIds)) {
                countMap = countMapper.selectByContentTypeAndIds(ContentTypeEnum.ARTICLE, articleIds);
            }
            Map<Long, Count> finalCountMap = countMap;
            List<ArticleVO> articleVOList = articleList.stream().map(e -> ArticleBeanConverter.toArticleVO(e, finalCountMap.get(e.getArticleId()))).collect(Collectors.toList());
            pageInfo.setList(articleVOList);
        }
        return pageInfo;
    }


}
