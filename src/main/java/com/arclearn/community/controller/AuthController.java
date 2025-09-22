package com.arclearn.community.controller;

import com.arclearn.community.common.result.Result;
import com.arclearn.community.common.utils.JwtUtils;
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

    // 登录请求参数
    @Data
    public static class LoginRequest {
        private String username;
        private String password;
    }
}