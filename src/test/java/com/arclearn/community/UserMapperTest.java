package com.arclearn.community;

import com.arclearn.community.entity.auth.Permission;
import com.arclearn.community.entity.auth.Role;
import com.arclearn.community.entity.auth.User;
import com.arclearn.community.mapper.auth.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // 使用实际配置的数据库（非内存库）
@Transactional // 测试完成后自动回滚，避免污染数据
@Rollback(true)
@SpringBootTest
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testFindByUsernameWithRolesAndPermissions() {
        // 调用查询方法
        String username = "user_test";
        String password = "test123";
        User user = userMapper.findByUsernameWithRolesAndPermissions(username).orElseThrow();

        // 验证基本信息
        assertNotNull(user, "查询结果不应为null");
        assertEquals(username, user.getUsername(), "用户名不匹配");
        assertEquals(password, user.getPassword(), "密码不匹配");
        assertTrue(user.isEnabled(), "用户应启用");

        // 验证角色关联
        assertNotNull(user.getRoles(), "角色列表不应为null");
        assertFalse(user.getRoles().isEmpty(), "用户应关联至少一个角色");

        Role role = user.getRoles().get(0);
        assertEquals("ROLE_TEST", role.getCode()); // 假设测试数据中的角色编码
        assertEquals("测试用户", role.getName());

        // 验证权限关联
        assertNotNull(role.getPermissions(), "权限列表不应为null");
        assertFalse(role.getPermissions().isEmpty(), "角色应关联至少一个权限");

        Permission permission = role.getPermissions().get(0);
        assertEquals("test:test", permission.getCode()); // 假设测试数据中的权限编码
        assertEquals("测试用户权限", permission.getName());

    }

}
