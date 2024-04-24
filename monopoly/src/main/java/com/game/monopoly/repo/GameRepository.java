package com.game.monopoly.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.game.monopoly.model.Game;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    Optional<Game> findByActive(boolean active);
}
