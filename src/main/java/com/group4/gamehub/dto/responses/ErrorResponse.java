package com.group4.gamehub.dto.responses;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorResponse {
    private final int code;
    private final String name;
    private final String description;
    private final LocalDateTime timestamp;
}
