package com.blogpress.article.service.impl;

import com.alibaba.fastjson.JSON;
import com.blogpress.article.bean.converter.ArticleBeanConverter;
import com.blogpress.article.bean.dto.ArticleDTO;
import com.blogpress.article.bean.entity.Article;
import com.blogpress.article.bean.response.ArticleVO;
import com.blogpress.article.dao.ArticleMapper;
import com.blogpress.article.request.CreateArticleRequest;
import com.blogpress.article.service.IArticleService;
import com.blogpress.common.bean.response.PageVO;
import com.blogpress.common.constants.RedisKeyConstants;
import com.blogpress.common.exception.BusinessException;
import com.blogpress.common.util.AssertUtils;
import com.blogpress.common.util.ContextHolder;
import com.blogpress.common.util.PageUtils;
import com.blogpress.common.util.bean.BeanCopyUtils;
import com.blogpress.count.bean.converter.CountBeanConverter;
import com.blogpress.count.bean.dto.CountDTO;
import com.blogpress.count.bean.response.CountVO;
import com.blogpress.count.enums.ContentTypeEnum;
import com.blogpress.count.service.ICountService;
import com.blogpress.user.bean.dto.UserDTO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
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
    private ICountService iCountService;

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ArticleVO createArticle(CreateArticleRequest request) {
        UserDTO user = ContextHolder.currentLoginUser();

        Article article = new Article();
        BeanCopyUtils.copy(request, article);
        article.setUserId(user.getUserId());
        int articleInsertCount = articleMapper.insert(article);

        CountDTO countDTO = iCountService.newCount(ContentTypeEnum.ARTICLE, article.getArticleId());

        if (articleInsertCount > 0 && countDTO != null) {
            // 插入文章主体
            Article createdArticle = articleMapper.selectById(article.getArticleId());
            ArticleDTO articleDTO = ArticleBeanConverter.toArticleDTO(createdArticle);

            ArticleVO articleVO = ArticleBeanConverter.toArticleVO(articleDTO);
            CountVO countVO = CountBeanConverter.toCountVO(countDTO);
            String articleKey = RedisKeyConstants.articleKey(article.getArticleId());

            redisTemplate.opsForValue().set(articleKey, articleDTO);
            articleVO.setCount(countVO);
            return articleVO;
        } else {
            log.error("Create article failed, param: {}", JSON.toJSONString(request));
            throw BusinessException.of("operate.failed");
        }
    }

    @Override
    public ArticleVO getArticleById(Long articleId) {
        // 从Redis取缓存数据，若有，直接返回
        String articleKey = RedisKeyConstants.articleKey(articleId);
        CountDTO countDTO = iCountService.getCount(ContentTypeEnum.ARTICLE, articleId);
        CountVO countVO = CountBeanConverter.toCountVO(countDTO);
        ArticleDTO articleDTO = (ArticleDTO) redisTemplate.opsForValue().get(articleKey);
        ArticleVO articleVO;
        if (articleDTO != null) {
            articleVO = ArticleBeanConverter.toArticleVO(articleDTO);
            articleVO.setCount(countVO);
            return articleVO;
        }
        // Redis没有数据，从数据库取，并且同步至Redis
        Article article = articleMapper.selectById(articleId);
        AssertUtils.isNotNull(article, "article.not.exist");


        articleDTO = ArticleBeanConverter.toArticleDTO(article);
        redisTemplate.opsForValue().set(articleKey, articleDTO);

        articleVO = ArticleBeanConverter.toArticleVO(articleDTO);
        articleVO.setCount(countVO);
        return articleVO;
    }

    @Override
    public Boolean deleteArticle(Long articleId) {
        UserDTO user = ContextHolder.currentLoginUser();
        Article article = articleMapper.selectById(articleId);
        AssertUtils.isNotNull(article, "article.not.exist");

        AssertUtils.equal(user.getUserId(), article.getUserId(), "no.permission.to.delete.article");

        int articleDeleteCount = articleMapper.deleteById(articleId);

        if(articleDeleteCount > 0){
            String articleKey = RedisKeyConstants.articleKey(articleId);
            redisTemplate.delete(articleKey);
            iCountService.deleteCount(ContentTypeEnum.ARTICLE, articleId);
            return true;
        } else {
            log.error("Delete article failed, articleId: {}", articleId);
            throw BusinessException.of("operate.failed");
        }
    }

    @Override
    public PageVO<ArticleVO> listUserArticles(Long userId, Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        Page<Article> articleList = articleMapper.selectUserArticlesByPage(userId);
        PageVO<ArticleVO> pageInfo = PageUtils.of(articleList, ArticleVO.class);
        if(!CollectionUtils.isEmpty(articleList)){
            List<Long> articleIds = articleList.stream().map(Article::getArticleId).collect(Collectors.toList());
            Map<Long, CountDTO> countMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(articleIds)) {
                countMap = iCountService.listCount(ContentTypeEnum.ARTICLE, articleIds);
            }
            Map<Long, CountDTO> finalCountMap = countMap;
            List<ArticleVO> articleVOList = articleList.stream().map(e -> {
                ArticleVO articleVO = ArticleBeanConverter.toArticleVO(e);
                CountDTO count = finalCountMap.get(e.getArticleId());
                CountVO countVO = CountBeanConverter.toCountVO(count);
                articleVO.setCount(countVO);
                return articleVO;
            }).collect(Collectors.toList());
            pageInfo.setList(articleVOList);
        }
        return pageInfo;
    }


}
