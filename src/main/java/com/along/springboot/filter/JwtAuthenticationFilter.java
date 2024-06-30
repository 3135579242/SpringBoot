package com.along.springboot.filter;

import com.along.springboot.service.JwtService;
import com.along.springboot.service.UserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


/**
 * 身份认证过滤器 Authentication
 * extends OncePerRequestFilter 每一个请求都需要认证
 */
@Configuration
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取授权信息
        String header = request.getHeader("Authorization");
        //授权信息为空 或 不以 Bearer 开头都是错误的
        if (header == null || !header.startsWith("Bearer ")) {
            //直接放行了因为，没有携带 token 不需要进行token的解析
            filterChain.doFilter(request, response);
            //return是防止过滤器链在返回的时候还执行以下解析token的代码
            // --------------------》
            // 《-------------------
            return;
        }
        //截取授权信息
        String jwt = header.substring(7);
        //提取jwt中包含的用户名
        String username = "";
        try {
            username = jwtService.extractUsername(jwt);
        }catch (Exception e){
            throw new RuntimeException("jwt不存在");
        }
        // 用户名不为空 同时 未进行任何验证
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // 获取到用户详细
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            // 用户详细不为空 同时 jwt 未过期
            if (userDetails != null && jwtService.isjwtValid(jwt)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        username,
                        userDetails.getPassword(),
                        userDetails.getAuthorities()
                );
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
            filterChain.doFilter(request, response);
        }
    }
}
