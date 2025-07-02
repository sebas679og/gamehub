package com.group4.gamehub.dto.responses.user;

import com.group4.gamehub.util.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class UserTournament {
    private String username;
    private Role role;
}
