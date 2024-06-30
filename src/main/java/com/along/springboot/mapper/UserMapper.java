package com.along.springboot.mapper;

import com.along.springboot.entity.User;
import com.along.springboot.entity.enum_.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据username查找用户
     * @param username 用户名称
     * @return 用户对象
     */
    //public abstract User findByUsername(String username);


}
