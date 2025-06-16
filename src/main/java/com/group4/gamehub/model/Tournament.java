package com.group4.gamehub.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="tournaments")
public class Tournament {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable=false)
    private String name;

    private int maxPlayers;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToMany
    private List<User> players = new ArrayList<>();
}
