package com.group4.gamehub.dto.requests.tournament;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TournamentsRequest {

    @NotBlank(message = "Tournaments list must not be blank or null.")
    @NotNull(message = "Tournaments list must not be blank or null.")
    private List<TournamentRequest> tournamentRequests;
}
