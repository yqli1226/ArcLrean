package com.arclearn.community.mapper.auth;

import com.arclearn.community.entity.auth.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.Optional;


public interface RoleMapper extends BaseMapper<Role> {

    Optional<Role> selectByCode(String code);
}
