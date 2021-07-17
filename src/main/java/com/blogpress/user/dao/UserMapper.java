package com.blogpress.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blogpress.user.bean.entity.User;
import org.apache.ibatis.annotations.Param;

/**
 * UserMapper
 * @author JY
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据昵称查找用户
     * @param nick 昵称
     * @return User
     */
    User getUserByNick(@Param("nick") String nick);

}
