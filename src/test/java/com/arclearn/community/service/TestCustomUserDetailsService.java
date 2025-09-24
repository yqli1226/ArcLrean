package com.arclearn.community.service;

import com.arclearn.community.entity.auth.Permission;
import com.arclearn.community.entity.auth.Role;
import com.arclearn.community.entity.auth.User;
import com.arclearn.community.mapper.auth.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TestCustomUserDetailsService {
    @Mock
    private UserMapper userMapper; // 模拟UserRepository

    @InjectMocks
    private CustomUserDetailsService userDetailsService; // 注入测试对象

    /**
     *  测试用户名存在时，正确加载用户信息及权限
     */
    @Test
    public void testLoadUserByUsername_UserExists() {
        // 准备测试数据
        String username = "testUser";
        String password = "123456";

        // 创建权限
        Permission permission = new Permission();
        permission.setCode("user:create");
        permission.setName("创建用户");

        // 创建角色
        Role role = new Role();
        role.setCode("ROLE_USER");
        role.setName("普通用户");
        role.getPermissions().add(permission);

        // 创建用户并关联角色
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.getRoles().add(role);
        user.setEnabled(true);

        // 模拟 UserMapper.findByUsername 方法返回用户信息
        when(userMapper.findByUsername(username)).thenReturn(Optional.of(user));

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        assertNotNull(userDetails);
        assertEquals(username, userDetails.getUsername());
        assertEquals(password, userDetails.getPassword());
        assertTrue(userDetails.isEnabled());

        // 验证权限（包含角色和具体权限）
        List<String> authorities = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        assertTrue(authorities.contains("ROLE_USER")); // 角色权限
        assertTrue(authorities.contains("user:create")); // 具体权限
    }

    /**
     * 测试用户名不存在时，抛出 UsernameNotFoundException 异常
     */
    @Test
    public void testLoadUserByUsername_UserNotFound() {
        // 1. 准备测试数据
        String username = "nonExistentUser";

        // 2. 模拟Repository行为（返回空Optional）
        when(userMapper.findByUsername(username)).thenReturn(Optional.empty());

        // 3. 执行测试并验证异常
        UsernameNotFoundException exception = assertThrows(
                UsernameNotFoundException.class,
                () -> userDetailsService.loadUserByUsername(username)
        );

        assertEquals("用户" + username + "不存在", exception.getMessage());
    }

    /**
     * 测试用户无角色和权限时，返回基础用户信息
     */
    @Test
    public void testLoadUserByUsername_NoRolesOrPermissions() {
        // 1. 准备测试数据（无角色和权限的用户）
        String username = "emptyUser";
        User user = new User();
        user.setUsername(username);
        user.setPassword("123456");
        user.setEnabled(true);
        user.setRoles(new ArrayList<>()); // 空角色列表

        // 2. 模拟Repository行为
        when(userMapper.findByUsername(username)).thenReturn(Optional.of(user));

        // 3. 执行测试方法
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        // 4. 验证结果（无权限但用户信息正确）
        assertEquals(username, userDetails.getUsername());
        assertTrue(userDetails.getAuthorities().isEmpty());
    }
}
