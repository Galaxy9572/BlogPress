package com.blogpress.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blogpress.user.bean.entity.UserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户角色Mapper
 * @author JY
 */
public interface UserRoleMapper extends BaseMapper<UserRole> {

    /**
     * 根据userId查询所有角色
     * @param userId 用户ID
     * @return 所有角色
     */
    List<UserRole> selectByUserId(@Param("userId") Long userId);

}
