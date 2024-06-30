package com.along.springboot.service;

import com.along.springboot.entity.User;
import com.along.springboot.entity.dto.AuthenticationRequest;
import com.along.springboot.entity.dto.RegisterRequest;
import com.along.springboot.entity.enum_.Role;
import com.along.springboot.entity.vo.AuthenticationResponse;
import com.along.springboot.mapper.UserMapper;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 身份验证管理器
     */
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserDetailsService userDetailsService;

    public User register(RegisterRequest registerRequest) {
        var user = User.builder()
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
//                .password(registerRequest.getPassword())
                //.role(Role.USER)
                .role(registerRequest.getRole())
                .build();
        userMapper.insert(user);
        return user;
    }

    public String login(AuthenticationRequest authenticationRequest) {
        //利用验证管理器验证
        Authentication authenticate = authenticationManager.authenticate(
                //验证输入的密码
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.username(),
                        authenticationRequest.password()
                )
        );
        //是否通过验证
        if (authenticate.isAuthenticated()) {
            return jwtService.generateJwt(userDetailsService.loadUserByUsername(authenticationRequest.username()));
        } else {
            //验证失败 is username not found
            throw new UsernameNotFoundException(authenticationRequest.username());
        }
    }
}
