package com.arclearn.community.controller;

import com.arclearn.community.common.result.Result;
import com.arclearn.community.common.utils.JwtUtils;
import com.arclearn.community.service.auth.UserService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private  AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService; // 新增的用户服务类

    @PostMapping("/login")
    public ResponseEntity<Result<String>> login(@RequestBody LoginRequest loginRequest) {
        try {
            // 1. 认证用户名密码
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 2. 生成JWT
            UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
            String jwt = jwtUtils.generateToken(userDetails);

            return new ResponseEntity<>(Result.success(jwt), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Result.fail(401, "认证失败"), HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Result<String>> register(@RequestBody LoginRequest loginRequest) {
        try {
            userService.register(loginRequest.getUsername(), loginRequest.getPassword());
            return new ResponseEntity<>(Result.success("注册成功"), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(Result.fail(400, e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(Result.fail(500, "注册失败"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 登录请求参数
    @Data
    public static class LoginRequest {
        private String username;
        private String password;
    }
}