package com.blogpress.search.service.impl;

import com.blogpress.article.bean.entity.Article;
import com.blogpress.common.bean.response.PageVO;
import com.blogpress.common.util.PageUtils;
import com.blogpress.search.bean.SearchBeanConverter;
import com.blogpress.search.bean.entity.SearchArticle;
import com.blogpress.search.bean.entity.SearchUser;
import com.blogpress.search.dao.ArticleRepository;
import com.blogpress.search.dao.UserRepository;
import com.blogpress.search.service.ISearchService;
import com.blogpress.user.bean.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * 搜索服务实现类
 * @author JY
 */
@Slf4j
@Service
public class SearchServiceImpl implements ISearchService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public void saveUser(User user) {
        SearchUser searchUser = SearchBeanConverter.toSearchUser(user);
        if (searchUser == null) {
            return;
        }
        userRepository.save(searchUser);
    }

    @Override
    public void saveArticle(Article article) {
        SearchArticle searchArticle = SearchBeanConverter.toSearchArticle(article);
        if (searchArticle == null) {
            return;
        }
        articleRepository.save(searchArticle);
    }

    @Override
    public PageVO<SearchUser> searchUserByNick(Integer pageNo, Integer pageSize, String nick){
        // ES分页从0开始
        Page<SearchUser> page = userRepository.findByNickContaining(PageRequest.of(pageNo - 1, pageSize), nick);
        return PageUtils.of(page);
    }

    @Override
    public PageVO<SearchArticle> searchArticle(Integer pageNo, Integer pageSize, String keyword){
        // ES分页从0开始
        Page<SearchArticle> page = articleRepository.findByTitleContainingOrContentContaining(PageRequest.of(pageNo - 1, pageSize), keyword, keyword);
        return PageUtils.of(page);
    }

    @Override
    public void deleteUserByUserId(Long userId){
        userRepository.deleteByUserId(userId);
    }

}
