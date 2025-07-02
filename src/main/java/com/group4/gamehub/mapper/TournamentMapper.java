package com.group4.gamehub.mapper;

import com.group4.gamehub.dto.responses.tournament.TournamentResponse;
import com.group4.gamehub.model.TournamentEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TournamentMapper {

    TournamentResponse toTournamentResponse(TournamentEntity entity);
}
