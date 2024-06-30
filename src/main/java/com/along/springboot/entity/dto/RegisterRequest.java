package com.along.springboot.entity.dto;


import com.along.springboot.entity.enum_.Role;
import lombok.Data;

@Data
public class RegisterRequest {

    private String username;

    private String password;

    private Role role;

}
