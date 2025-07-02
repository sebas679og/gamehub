package com.group4.gamehub.dto.responses.match;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MatchsResponse {
    private List<MatchResponse> matchResponses;
}
