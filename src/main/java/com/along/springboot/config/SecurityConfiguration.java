package com.along.springboot.config;


import com.along.springboot.filter.JwtAuthenticationFilter;
import com.along.springboot.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    /**
     * 此处的  UserDetailsService  已经实现 Security 的 UserDetailsService
     */
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * SecurityFilterChain 安全过滤链 放行API和限制API
     *
     * @param httpSecurity
     * @return
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                //验证网络请求
                .authorizeHttpRequests(registry -> {
                    //注册表
                    registry
                            //这个接口数据//被全部用户访问
                            .requestMatchers("/api/first/firstString").permitAll()
                            .requestMatchers("/api/v1/auth/register").permitAll()
                            .requestMatchers("/api/v1/auth/login").permitAll()
                            //其他任意接口都需要认证
                            .anyRequest().authenticated();
                })
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin(formLogin -> {
                    formLogin.permitAll();
                })
                .csrf(c -> {
                    c.disable();
                })
                .build();
    }

    @Bean
    public org.springframework.security.core.userdetails.UserDetailsService userDetailsService() {
        return userDetailsService;
    }

    /**
     * 身份验证程序
     *
     * @return
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        //因为使用的是数据库数据 DaoAuthenticationProvider
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        //给身份验证程序设置来源的信息
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        //给身份验证程序设置密码是用什么加密和解密的
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    /**
     * 验证管理器
     *
     * @return
     */
    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(authenticationProvider());
    }

    /**
     * 因为Security的密码默认是通过加密器的 我们要定义 BCryptPasswordEncoder
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
