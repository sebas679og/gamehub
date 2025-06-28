package com.group4.gamehub.mapper;

import com.group4.gamehub.dto.responses.MatchResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.group4.gamehub.model.MatchEntity;

@Mapper(componentModel = "spring")
public interface MatchMapper {

    @Mapping(source = "tournament.name", target = "tournamentName")
    @Mapping(source = "player1.username", target = "player1")
    @Mapping(source = "player2.username", target = "player2")
    @Mapping(source = "result", target = "result")
    MatchResponse toMatchResponse(MatchEntity entity);
}
