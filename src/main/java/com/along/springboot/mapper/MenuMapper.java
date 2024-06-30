package com.along.springboot.mapper;

import com.along.springboot.entity.Menu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Set;

/**
 * RBAC角色权限表
 */

@Mapper
public interface MenuMapper {


    Set<String> selectPermsByUserId(Integer userId);


}
