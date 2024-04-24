package com.game.monopoly.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.game.monopoly.model.Player;

@Repository
public interface PlayerRepository extends JpaRepository<Player, String> {

}

