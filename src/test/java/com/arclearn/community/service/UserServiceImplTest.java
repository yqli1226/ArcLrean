package com.arclearn.community.service;


import com.arclearn.community.common.enmu.RoleEnum;
import com.arclearn.community.controller.AuthController;
import com.arclearn.community.entity.auth.Role;
import com.arclearn.community.entity.auth.User;
import com.arclearn.community.entity.auth.UserRole;
import com.arclearn.community.mapper.auth.RoleMapper;
import com.arclearn.community.mapper.auth.UserMapper;
import com.arclearn.community.mapper.auth.UserRoleMapper;
import com.arclearn.community.service.auth.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Transactional
public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Mock
    private UserMapper userMapper;

    @Mock
    private UserRoleMapper userRoleMapper;

    @Mock
    private RoleMapper roleMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Captor
    private ArgumentCaptor<User> userCaptor;

    @Captor
    private ArgumentCaptor<UserRole> userRoleCaptor;

    @Test
    public void testRegister_Success() {
        // 准备测试数据
        String username = "testUser";
        String password = "testPassword";
        String encodedPassword = "encodedPassword123";
        Role defaultRole = mockDefaultRole();

        // 模拟行为
        when(userMapper.findByUsername(username)).thenReturn(Optional.empty());
        when(roleMapper.selectByCode(RoleEnum.ROLE_USER.getCode())).thenReturn(Optional.of(defaultRole));
        when(passwordEncoder.encode(password)).thenReturn(encodedPassword);

        userServiceImpl.register(username, password);

        // 验证用户是否被正确保存
        verify(userMapper, times(1)).insert(userCaptor.capture());
        User savedUser = userCaptor.getValue();

        assertNotNull(savedUser, "保存的用户不能为null");
        assertEquals(username, savedUser.getUsername(), "用户名应正确设置");
        assertEquals(encodedPassword, savedUser.getPassword(), "密码应被加密");
        assertNotNull(savedUser.getUuid(), "用户UUID应自动生成");
        assertTrue(savedUser.isEnabled(), "用户默认应启用");

        // 验证用户角色是否被正确保存
        verify(userRoleMapper, times(1)).insert(userRoleCaptor.capture()); // 确保关联插入被调用1次
        UserRole userRole = userRoleCaptor.getValue(); // 获取被插入的关联对象

        assertNotNull(userRole, "用户角色关联记录不能为null");
        assertEquals(savedUser.getId(), userRole.getUserId(), "关联记录的用户ID应与用户ID一致");
        assertEquals(defaultRole.getId(), userRole.getRoleId(), "关联记录的角色ID应与默认角色ID一致");
    }

    @Test
    public void testRegister_UserNameExists() {
        String username = "existingUser";

        when(userMapper.findByUsername(username)).thenReturn(Optional.of(new User()));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> userServiceImpl.register(username, "password")
        );

        assertEquals("用户名已被占用", exception.getMessage());
        verify(userMapper, never()).insert(any(User.class));
        verify(userRoleMapper, never()).insert(any(UserRole.class));
    }

    @Test
    public void testRegister_DefaultRoleNotFound() {
        String username = "testUser";
        String password = "testPassword";

        when(userMapper.findByUsername(username)).thenReturn(Optional.empty());
        when(roleMapper.selectByCode(RoleEnum.ROLE_USER.getCode())).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> userServiceImpl.register(username, password)
        );

        assertEquals("默认角色不存在", exception.getMessage(), "异常信息不符合预期");
        verify(userMapper, never()).insert(any(User.class));
        verify(userRoleMapper, never()).insert(any(UserRole.class));
    }

    private Role mockDefaultRole() {
        Role role = new Role();
        role.setId(1L);
        role.setCode(RoleEnum.ROLE_USER.getCode());
        role.setName(RoleEnum.ROLE_USER.getName());
        return role;
    }

}
