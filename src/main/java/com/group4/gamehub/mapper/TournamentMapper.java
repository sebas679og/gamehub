package com.group4.gamehub.mapper;

import com.group4.gamehub.dto.responses.tournament.TournamentBasic;
import com.group4.gamehub.dto.responses.tournament.TournamentDetails;
import com.group4.gamehub.dto.responses.tournament.TournamentPlayers;
import com.group4.gamehub.model.TournamentEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TournamentMapper {

    TournamentPlayers toTournamentResponse(TournamentEntity entity);

    TournamentBasic toTournamentBasicResponse(TournamentEntity entity);

    TournamentDetails toTournamentDetailsResponse(TournamentEntity entity);
}
