package com.arclearn.community.common.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
/**
 * @Author: rongqi
 * @Description: JWT工具类 用于生成、解析、验证JWT令牌
 */
@Component
public class JwtUtils {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    private Key key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(UserDetails userDetails) {
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        List<String> roles = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        // 提取权限信息（去掉ROLE_前缀的）
        List<String> permissions = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .filter(auth -> !auth.startsWith("ROLE_"))
                .toList();

        return Jwts.builder()
                .setSubject(userDetails.getUsername()) // 用户名
                .claim("roles", roles)  //角色信息
                .claim("permissions", permissions)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key, SignatureAlgorithm.HS256) // 签名算法
                .compact();
    }

    /**
     * 从JWT令牌中获取用户名
     * @param token JWT令牌字符串
     * @return 解析出的用户名（Subject）
     */
    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    /**
     * 验证JWT令牌的有效性
     * @param token 需要验证的JWT令牌
     * @return 如果令牌有效且匹配用户信息则返回true，否则返回false
     */
    public boolean validateToken(String token) {
        try {
        // 使用JWT解析器构建器配置解析器
            Jwts.parserBuilder()
                // 设置签名密钥
                    .setSigningKey(key)
                // 构建解析器
                    .build()
                // 解析并验证令牌
                    .parseClaimsJws(token);

        // 令牌验证成功，返回true
            return true;
        } catch (Exception e ) {
            // 令牌无效
            return false;
        }
    }

}
