package com.arclearn.community.service.auth.impl;

import com.arclearn.community.common.enmu.RoleEnum;
import com.arclearn.community.entity.auth.Role;
import com.arclearn.community.entity.auth.User;
import com.arclearn.community.entity.auth.UserRole;
import com.arclearn.community.mapper.auth.RoleMapper;
import com.arclearn.community.mapper.auth.UserMapper;
import com.arclearn.community.mapper.auth.UserRoleMapper;
import com.arclearn.community.service.auth.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void register(String username, String password) {
        // 检查用户名是否已经存在
        if(userMapper.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("用户名已被占用");
        }
        // 查询默认角色
        Role defaultRole = roleMapper.selectByCode(RoleEnum.ROLE_USER.getCode())
                .orElseThrow(() -> new IllegalArgumentException("默认角色不存在"));

        // 创建用户 加密密码
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRoles(Collections.singletonList(defaultRole));
        user.generateUuid();

        // 保存用户
        userMapper.insert(user);

        // 5. 向中间表user_role插入关联记录（用户ID + 角色ID）
        UserRole userRole = new UserRole();
        userRole.setUserId(user.getId()); // 用户保存后会自动生成id
        userRole.setRoleId(defaultRole.getId()); // 角色ID
        userRoleMapper.insert(userRole); // 插入中间表
    }
}
