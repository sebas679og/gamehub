package com.group4.gamehub.dto.responses.match;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Wrapper DTO for returning a list of match responses.
 *
 * <p>This class is typically used to return multiple match results, such as after generating
 * pairings for a tournament or listing all matches.
 */
@Getter
@Setter
@Builder
public class MatchsResponse {

  /** A list of individual match response objects. */
  private List<MatchResponse> matchResponses;
}
