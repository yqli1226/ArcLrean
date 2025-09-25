package com.arclearn.community.mapper.auth;

import com.arclearn.community.entity.auth.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

public interface UserMapper extends BaseMapper<User> {

    Optional<User> findByUsername(String username);


    Optional<User> findByUsernameWithRolesAndPermissions(@Param("username") String username);


    void save(User user);
}
