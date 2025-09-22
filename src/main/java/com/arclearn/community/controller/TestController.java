package com.arclearn.community.controller;

import com.arclearn.community.common.result.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    // 所有认证用户可访问（只要登录就能进）
    @GetMapping("/public")
    public ResponseEntity<Result<String>> publicApi() {
        System.out.println("这是公开接口（需登录）");
        return new ResponseEntity<>(Result.success("这是公开接口（需登录）"), HttpStatus.OK);
    }

    // 只有ROLE_USER角色可访问
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user")
    public ResponseEntity<Result<String>> userApi() {
        System.out.println("这是用户接口");
        return new ResponseEntity<>(Result.success("这是用户接口"), HttpStatus.OK);
    }

    // 只有ROLE_ADMIN角色可访问
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public ResponseEntity<Result<String>> adminApi() {
        System.out.println("这是管理员接口");
        return new ResponseEntity<>(Result.success("这是管理员接口"), HttpStatus.OK);
    }

    // 只有user:create权限可访问
    @PreAuthorize("hasAuthority('user:create')")
    @GetMapping("/create")
    public ResponseEntity<Result<String>> createApi() {
        System.out.println("这是创建用户接口");
        return new ResponseEntity<>(Result.success("这是创建用户接口"), HttpStatus.OK);
    }
}