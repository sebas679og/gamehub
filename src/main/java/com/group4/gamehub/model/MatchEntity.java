package com.group4.gamehub.model;


import com.group4.gamehub.util.Result;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "match_tbl")
public class MatchEntity {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @ManyToOne(targetEntity = TournamentEntity.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "tournament_id", nullable = false)
    private TournamentEntity tournament;

    @ManyToOne
    @JoinColumn(name = "player1_id", nullable = false)
    private UserEntity player1;

    @ManyToMany
    @JoinColumn(name = "player2_id", nullable = false)

    //TODO: review relations
    @JoinTable(joinColumns = @JoinColumn(name = "id_match"),inverseJoinColumns = @JoinColumn(name = "user_id"))
    private UserEntity player2;

    @Enumerated(EnumType.STRING)
    private Result result;

    private int round;

}
