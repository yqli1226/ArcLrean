package com.arclearn.community.service;

import com.arclearn.community.entity.auth.Permission;
import com.arclearn.community.entity.auth.Role;
import com.arclearn.community.entity.auth.User;
import com.arclearn.community.mapper.auth.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 加载用户信息
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("加载用户：" + username); // 日志
        // 从数据库中查询用户
        User user = userMapper.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("用户"+ username +"不存在"));

        // 打印用户关联的角色
        System.out.println("用户角色：" + user.getRoles().stream()
                .map(Role::getName)
                .toList());

        // 获取所有权限（包括角色和具体权限）
        Collection<GrantedAuthority> authorities = getAuthorities(user);

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.isEnabled(),
                true,
                true,
                true,
                authorities
        );
    }

    /**
     * 获取用户的所有权限（包括角色和具体权限）
     */
    private Collection<GrantedAuthority> getAuthorities(User user) {
        List<GrantedAuthority> authorities = new ArrayList<>();

        for (Role role : user.getRoles()) {
            // 添加角色
            authorities.add(new SimpleGrantedAuthority(role.getCode()));

            // 添加该角色对应的所有权限
            for (Permission permission : role.getPermissions()) {
                authorities.add(new SimpleGrantedAuthority(permission.getCode()));
            }
        }

        return authorities;
    }
}
