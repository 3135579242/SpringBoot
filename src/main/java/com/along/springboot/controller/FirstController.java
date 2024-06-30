package com.along.springboot.controller;


import com.along.springboot.entity.First;
import com.along.springboot.mapper.FirstMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 测试新项目
 */
@RestController
@RequestMapping("/api/first")
public class FirstController {

    private FirstMapper firstMapper;

    @Autowired
    public FirstController(FirstMapper firstMapper) {
        this.firstMapper = firstMapper;
    }

    /**
     * 测试
     *
     * @return
     */
    @GetMapping("/firstString")
    public String firstString() {
        return "成功";
    }

    /**
     * 测试
     *
     * @return
     */
    @GetMapping("/RoleUSER")
    public String firstUser() {
        return "Role USER";
    }


    /**
     * 测试
     *
     * @return
     */
    @GetMapping("/RoleAdmin")
    public String firstAdmin() {
        return "Role Admin";
    }


    /**
     * 连接数据库案例
     *
     * @return
     */
    @GetMapping("/listFirsts")
    public List<First> firstListFirsts() {
        List<First> firsts = firstMapper.selectList(null);
        return firsts;
    }


}
