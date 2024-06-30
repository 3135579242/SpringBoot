package com.along.springboot.service;

import com.along.springboot.entity.Menu;
import com.along.springboot.entity.User;
import com.along.springboot.entity.enum_.Role;
import com.along.springboot.mapper.MenuMapper;
import com.along.springboot.mapper.UserMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.jsonwebtoken.lang.Arrays;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service("AUserDetailsService")
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Resource
    private UserMapper userMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getUsername, username);
        //查询数据库得到的用户数据
        User user = userMapper.selectOne(userLambdaQueryWrapper);
        if (!(Objects.isNull(user))) {
            //查询权限
            //Set<String> perms = menuMapper.selectPermsByUserId(user.getId());
            //构造一个实现了UserDetails的对象(也可以自定义)
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUsername())
//                    .password("$2a$10$MNK6Dj9Zlg1Zenbvpd.OSOLX/nd7Q26kjzeOXOFXCtw4Whrar7uZK")
                    .password(user.getPassword())
//                    .roles(user.getRole().name())
//                    .authorities()
                    .build();
        } else {
            throw new UsernameNotFoundException(username);
        }
    }

    public String[] getRoles(User user) {
        if (user != null) {
            return new String[]{user.getRole().name()};
        }
        return user.getRole().name().split(",");
    }

}
