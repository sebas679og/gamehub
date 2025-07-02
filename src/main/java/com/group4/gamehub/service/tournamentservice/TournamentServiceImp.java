package com.group4.gamehub.service.tournamentservice;

import com.group4.gamehub.dto.requests.tournament.TournamentsRequest;
import com.group4.gamehub.dto.responses.tournament.TournamentResponse;
import com.group4.gamehub.dto.responses.tournament.TournamentsResponse;
import com.group4.gamehub.mapper.TournamentMapper;
import com.group4.gamehub.model.TournamentEntity;
import com.group4.gamehub.repository.TournamentRepository;
import com.group4.gamehub.util.SlugUtil;
import com.group4.gamehub.util.Status;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class TournamentServiceImp implements TournamentService{

    private final TournamentRepository tournamentRepository;
    private final TournamentMapper tournamentMapper;

    public TournamentServiceImp(TournamentRepository tournamentRepository, TournamentMapper tournamentMapper) {
        this.tournamentRepository = tournamentRepository;
        this.tournamentMapper = tournamentMapper;
    }

    @Override
    public TournamentsResponse createTournaments(TournamentsRequest request) {
        List<TournamentResponse> responses = request.getTournamentRequests().stream()
                .map(tournament -> {
                    String slug = SlugUtil.toUniqueSlug(
                            tournament.getName(),
                            tournamentRepository::existsBySlug
                    );
                    TournamentEntity entity = TournamentEntity.builder()
                            .name(tournament.getName())
                            .maxPlayers(tournament.getMaxPlayers())
                            .slug(slug)
                            .status(Status.CREATED)
                            .build();
                    TournamentEntity savedEntity = tournamentRepository.save(entity);
                    return tournamentMapper.toTournamentResponse(savedEntity);
                })
                .collect(Collectors.toList());

        return TournamentsResponse.builder()
                .tournamentResponses(responses)
                .build();
    }
}
