package com.java.xdd.shiro.mapper;

import com.github.abel533.mapper.Mapper;
import com.java.xdd.shiro.domain.Permission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PermissionMapper extends Mapper<Permission>{

    /**
     * 根据用户id获取用户权限(即url)
     * @param id
     * @return
     */
    List<Permission> findPermissionListByUserId(@Param("id") Long id);

    /**
     * 保存url
     * @param permission
     * @return
     */
    Integer save(Permission permission);

}
