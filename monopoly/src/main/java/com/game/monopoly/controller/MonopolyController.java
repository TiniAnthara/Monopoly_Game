package com.game.monopoly.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.game.monopoly.model.Game;
import com.game.monopoly.model.Player;
import com.game.monopoly.model.RollResult;
import com.game.monopoly.service.GameService;

@RestController
@RequestMapping("/api/monopoly")
public class MonopolyController {

    @Autowired
    private GameService gameService;

    @PostMapping("/create-game")
    public ResponseEntity<String> createGame() {
        Game game = gameService.createGame();
        return ResponseEntity.ok("Game created successfully. Game ID: " + game.getId());
    }

    @PostMapping("/join-game/{gameId}")
    public ResponseEntity<String> joinGame(@PathVariable Long gameId, @RequestBody String playerName) {
        gameService.joinGame(gameId, playerName);
        return ResponseEntity.ok("Joined game successfully!");
    }

    @PostMapping("/roll-die/{playerId}")
    public ResponseEntity<com.game.monopoly.service.RollResult> rollDie(@PathVariable String playerId) {
        com.game.monopoly.service.RollResult result = gameService.rollDie(playerId);
        return ResponseEntity.ok(result); // Returning the whole RollResult object
    }

    @GetMapping("/check-winner") 
    public ResponseEntity<String> checkWinner() {
        String winner = gameService.checkWinner();
        if (winner != null) {
            return ResponseEntity.ok(winner + " wins!");
        } else {
            return ResponseEntity.ok("Game ongoing.");
        }
    }
}
