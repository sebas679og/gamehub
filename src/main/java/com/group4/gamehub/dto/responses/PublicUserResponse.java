package com.group4.gamehub.dto.responses;

import com.group4.gamehub.util.Role;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PublicUserResponse {
  private String username;
  private Role role;
  private String rank;
  private Long points;
}
