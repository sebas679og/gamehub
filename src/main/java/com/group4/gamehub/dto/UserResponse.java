package com.group4.gamehub.dto;

import com.group4.gamehub.util.Role;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponse {
    private String username;
    private String email;
    private Role role;
    private String rank;
    private Long points;
}
