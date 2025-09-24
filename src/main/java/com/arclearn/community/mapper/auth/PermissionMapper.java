package com.arclearn.community.mapper.auth;

import com.arclearn.community.entity.auth.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {

}
