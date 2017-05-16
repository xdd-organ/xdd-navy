package com.java.xdd.shiro.mapper;

import com.java.xdd.shiro.domain.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper{

    /**
     * 根据用户查询所有角色
     * @param userId 用户id
     * @return
     */
    List<Role> findRoleByUserId(@Param("userId") Long userId);
}
