package com.blogpress.search.bean;

import com.blogpress.article.bean.entity.Article;
import com.blogpress.common.util.bean.BeanCopyUtils;
import com.blogpress.search.bean.entity.SearchArticle;
import com.blogpress.search.bean.entity.SearchUser;
import com.blogpress.user.bean.entity.User;

/**
 * @author JY
 */
public class SearchBeanConverter {

    public static SearchUser toSearchUser(User user){
        if(user == null){
            return null;
        }
        SearchUser searchUser = new SearchUser();
        BeanCopyUtils.copy(user, searchUser);
        return searchUser;
    }

    public static SearchArticle toSearchArticle(Article article){
        if(article == null){
            return null;
        }
        SearchArticle searchArticle = new SearchArticle();
        BeanCopyUtils.copy(article, searchArticle, true);
        return searchArticle;
    }

}
