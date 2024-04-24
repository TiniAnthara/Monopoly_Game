package com.game.monopoly.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.game.monopoly.model.Game;
import com.game.monopoly.model.Place;
import com.game.monopoly.model.Player;
import com.game.monopoly.repo.GameRepository;
import com.game.monopoly.repo.PlayerRepository;

@Service
public class GameServiceImpl  {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private PlaceService placeService;

    public Game createGame() {
        Game game = new Game();
        game.setActive(true);
        game.setBoard(placeService.createBoard()); 
        gameRepository.save(game);
        return game;
    }

    public void joinGame(Long gameId, String playerName) {
        Game game = gameRepository.findById(gameId).orElseThrow(() -> new IllegalArgumentException("Invalid Game ID"));
        if (game.getPlayer2() != null) {
            throw new IllegalStateException("Game already full");
        }
        Player player2 = new Player();
        player2.setName(playerName);
        player2.setCash(1000); // Starting cash
        player2.setCurrentPosition("Start");
        game.setPlayer2(player2);
        playerRepository.save(player2);
        gameRepository.save(game);
    }

    public RollResult rollDie(String playerId) {
        Game game = getActiveGame();
        Player player = getPlayerById(playerId);
        if (!game.getCurrentTurn().equals(playerId)) {
            throw new IllegalStateException("Not your turn!");
        }

        int roll1 = new Random().nextInt(6) + 1;
        int roll2 = new Random().nextInt(6) + 1;
        int totalRoll = roll1 + roll2;
        String newPosition = movePlayer(player, totalRoll);
        RollResult result = new RollResult(roll1, roll2, newPosition);
        if (newPosition.equals("Start")) {
            player.setCash(player.getCash() + 200);
            result.setMessage("You crossed Start! Gained $200.");
        } else {
            Place landedPlace = placeService.getPlaceByName(newPosition);
            if (landedPlace.getOwner() != null && !landedPlace.getOwner().equals(player)) {
                int rent = landedPlace.getRent();
                player.setCash(player.getCash() - rent);
                result.setMessage("You landed on " + newPosition + " owned by " + landedPlace.getOwner().getName()
                        + ". Paid rent of $" + rent + ".");
            } else {
                if (landedPlace.getOwner() == null) {
                    player.setCash(player.getCash() - landedPlace.getBuy());
                    landedPlace.setOwner(player);
                    result.setMessage("You landed on unclaimed place " + newPosition + ". Bought it for $" + landedPlace.getBuy() + ".");
                }
            }
        }
        game.setCurrentTurn(getNextTurn(playerId)); // Update current turn
        updateGame(game);
        return result;
    }

    private Long getNextTurn(String currentTurn) {
        return currentTurn.equals(gameRepository.findById(1L).get().getPlayer1().getId()) ?
                gameRepository.findById(1L).get().getPlayer2().getId() :
                gameRepository.findById(1L).get().getPlayer1().getId();
    }

    private Player getPlayerById(String playerId) {
        return playerRepository.findById(playerId).orElseThrow(() -> new IllegalArgumentException("Invalid Player ID"));
    }

    private Game getActiveGame() {
        return gameRepository.findByActive(true).orElseThrow(() -> new IllegalStateException("No active game found"));
    }

    private String movePlayer(Player player, int totalRoll) {
        int boardSize = 10; // Assuming 10 places on the board
        int newPositionIndex = (player.getCurrentPosition().equals("Start") ? 0 : 1) + (player.getBoardPosition() + totalRoll) % boardSize;
        String newPosition = newPositionIndex == 0 ? "Start" : placeService.getPlaceNames().get(newPositionIndex - 1);
        player.setBoardPosition(newPositionIndex); // Update player's board position (0-indexed)
        return newPosition;
    }

    private void updateGame(Game game) {
        gameRepository.save(game);
    }

   
}
