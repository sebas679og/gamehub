package com.group4.gamehub.dto;

import com.group4.gamehub.model.Role;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PublicUserResponse {
    private String username;
    private Role role;
    private String rank;
    private int points;
}
