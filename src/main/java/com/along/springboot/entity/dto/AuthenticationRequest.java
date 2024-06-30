package com.along.springboot.entity.dto;

import lombok.Builder;
import lombok.Data;


@Builder
public record AuthenticationRequest(String username, String password) {
}
