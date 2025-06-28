package com.group4.gamehub.dto.requests;

import com.group4.gamehub.util.Result;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


/**
 * Data Transfer Object (DTO) for updating the result of a match.
 * <p>
 * This object is used in PUT requests to update the outcome of a match,
 * specifying the result (e.g., PLAYER1_WIN, PLAYER2_WIN, PENDING).
 */
@Getter
@Setter
public class MatchRequest {

    /**
     * The result of the match.
     * <p>
     * Must not be blank and must match a valid {@link Result} enum value.
     */
    @Schema(
            description = "The result of the match",
            example = "PLAYER1_WIN",
            allowableValues = {"PLAYER1_WIN", "PLAYER2_WIN", "DRAW"}
    )
    @NotNull(message = "Result must not be null")
    @NotBlank(message = "Result must not be blank")
    private Result result;
}
