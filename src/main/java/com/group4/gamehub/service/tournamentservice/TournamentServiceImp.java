package com.group4.gamehub.service.tournamentservice;

import com.group4.gamehub.dto.requests.tournament.TournamentsRequest;
import com.group4.gamehub.dto.responses.tournament.JoinTournament;
import com.group4.gamehub.dto.responses.tournament.TournamentBasic;
import com.group4.gamehub.dto.responses.tournament.TournamentDetails;
import com.group4.gamehub.dto.responses.tournament.TournamentsResponse;
import com.group4.gamehub.exception.NotFoundException;
import com.group4.gamehub.mapper.TournamentMapper;
import com.group4.gamehub.model.TournamentEntity;
import com.group4.gamehub.model.UserEntity;
import com.group4.gamehub.repository.TournamentRepository;
import com.group4.gamehub.repository.UserRepository;
import com.group4.gamehub.util.SlugUtil;
import com.group4.gamehub.util.Status;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class TournamentServiceImp implements TournamentService{

    private final TournamentRepository tournamentRepository;
    private final TournamentMapper tournamentMapper;
    private final UserRepository userRepository;

    public TournamentServiceImp(TournamentRepository tournamentRepository, TournamentMapper tournamentMapper, UserRepository userRepository) {
        this.tournamentRepository = tournamentRepository;
        this.tournamentMapper = tournamentMapper;
        this.userRepository = userRepository;
    }

    @Override
    public TournamentsResponse createTournaments(TournamentsRequest request) {
        List<TournamentBasic> responses = request.getTournamentRequests().stream()
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
                .tournaments(responses)
                .build();
    }

    @Override
    public TournamentsResponse getTournaments() {
        List<TournamentBasic> tournaments = tournamentRepository.findAllByStatus(Status.CREATED)
                .stream()
                .map(tournamentMapper::toTournamentBasicResponse)
                .collect(Collectors.toList());

        return TournamentsResponse.builder()
                .tournaments(tournaments)
                .build();
    }

    @Override
    public TournamentDetails getTournamentDetails(String slug) {
        return tournamentRepository.findBySlug(slug)
                .map(tournamentMapper::toTournamentDetailsResponse)
                .orElseThrow(() -> new NotFoundException("Tournament not found"));
    }

    @Override
    public JoinTournament joinTournament(String username, String slug) {
        TournamentEntity tournament = tournamentRepository.findBySlug(slug)
                .orElseThrow(() -> new NotFoundException("Tournament not found"));

        if (tournament.getStatus() != Status.CREATED){
            throw new RuntimeException("The tournament does not allow the union of more players");
        }

        boolean alreadyJoined = tournament.getUserEntities().stream()
                .anyMatch(user -> user.getUsername().equals(username));

        if (alreadyJoined){
            throw new RuntimeException("The user is already joined to the tournament");
        }

        if (tournament.getUserEntities().size() >= tournament.getMaxPlayers()) {
            throw new RuntimeException("The tournament reached the maximum of players");
        }

        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found"));

        tournament.getUserEntities().add(user);
        tournamentRepository.save(tournament);

        return JoinTournament.builder()
                .message("Successfully linked user")
                .tournamentSlug(tournament.getSlug())
                .tournamentName(tournament.getName())
                .username(username)
                .build();
    }
}
