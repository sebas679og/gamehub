package com.group4.gamehub.dto.responses;

import java.time.Instant;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorResponse {
    private final int code;
    private final String name;
    private final String description;
    private final String uri;
    private final String method;
    private final Instant timestamp;
}
