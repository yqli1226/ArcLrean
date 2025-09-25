package com.arclearn.community.common.config;

import com.arclearn.community.common.utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {


    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {

            // 排除登录路径
            if ("/login".equals(request.getServletPath())|| "/register".equals(request.getServletPath())) {
                filterChain.doFilter(request, response);
                return;
            }
            // 1. 从Header中获取JWT
            String jwt = parseJwt(request);

            if (jwt == null) {
                // 没有提供token，返回401错误
                sendErrorResponse(response, "未提供认证token");
                return; // 重要：这里要return！
            }

            // 2. 验证JWT
            if (!jwtUtils.validateToken(jwt)) {
                // token无效，返回401错误
                sendErrorResponse(response, "token无效或已过期");
                return; // 重要：这里要return！
            }

            // 2. 解析用户名并加载用户信息
            String username = jwtUtils.getUsernameFromToken(jwt);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // 3. 如果JWT有效，使用Spring Security的上下文设置用户信息
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e);
            return;
        }

        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }
        return null;
    }

    private void sendErrorResponse(HttpServletResponse response, String errorMessage) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"error\": \"" + errorMessage + "\"}");
        response.getWriter().flush();
    }
}
