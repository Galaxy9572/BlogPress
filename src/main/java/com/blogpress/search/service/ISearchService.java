package com.blogpress.search.service;

import com.blogpress.article.bean.entity.Article;
import com.blogpress.common.bean.response.PageVO;
import com.blogpress.search.bean.entity.SearchArticle;
import com.blogpress.search.bean.entity.SearchUser;
import com.blogpress.user.bean.entity.User;

/**
 * 搜索服务
 * @author JY
 */
public interface ISearchService {

    /**
     * 保存用户
     * @param user User
     */
    void saveUser(User user);

    /**
     * 保存文章
     * @param article Article
     */
    void saveArticle(Article article);

    /**
     * 根据昵称分页搜索用户
     * @param pageNo 当前页
     * @param pageSize 页大小
     * @param nick 昵称
     * @return PageVO<SearchUser>
     */
    PageVO<SearchUser> searchUserByNick(Integer pageNo, Integer pageSize, String nick);

    /**
     * 根据关键字分页搜索文章
     * @param pageNo 当前页
     * @param pageSize 页大小
     * @param keyword 关键字
     * @return PageVO<SearchUser>
     */
    PageVO<SearchArticle> searchArticle(Integer pageNo, Integer pageSize, String keyword);

    /**
     * 根据用户ID删除用户
     * @param userId 用户ID
     */
    void deleteUserByUserId(Long userId);
}
