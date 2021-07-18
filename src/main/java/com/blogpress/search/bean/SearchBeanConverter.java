package com.blogpress.search.bean;

import com.blogpress.common.util.bean.BeanCopyUtils;
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

}
