package com.group4.gamehub.dto.requests.tournament;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TournamentRequest {

    @NotBlank(message = "Name must not be blank or null.")
    @NotNull(message = "Name must not be blank or null.")
    private String name;

    @Min(value = 2, message = "The maximum number of players must be greater than or equal to 2.")
    @JsonProperty(value = "max_players")
    @Builder.Default
    private Integer maxPlayers = 2;
}
