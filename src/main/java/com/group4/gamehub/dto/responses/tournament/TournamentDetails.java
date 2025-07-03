package com.group4.gamehub.dto.responses.tournament;

import com.group4.gamehub.dto.responses.match.MatchResponse;
import com.group4.gamehub.dto.responses.user.UserTournament;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * DTO representing detailed information about a tournament.
 *
 * <p>Extends {@link TournamentPlayers} and includes lists of matches and participating users.
 */
@Setter
@Getter
@SuperBuilder
public class TournamentDetails extends TournamentPlayers {

  /** List of matches associated with the tournament. */
  private List<MatchResponse> matches;

  /** List of users participating in the tournament. */
  private List<UserTournament> users;
}
