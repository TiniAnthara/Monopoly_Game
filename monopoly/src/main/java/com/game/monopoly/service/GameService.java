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
public class GameService {
	@Autowired
    public PlayerRepository playerRepository;

    @Autowired
    public GameRepository gameRepository;

    @Autowired
    public PlaceService placeService;

    public Game createGame() {
        Game game = new Game();
        game.setActive(true);
        game.setBoard(placeService.createBoard()); // Initialize the board with places
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

    public RollResult rollDie(Long playerId) {
        Game game = getActiveGame();
        Player player = getPlayerById(playerId);
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
        game.setCurrentTurn(playerId); // Update current turn
        updateGame(game);
        return result;
    }

    private Player getPlayerById(Long playerId) {
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

    public String checkWinner() {
        Game game = getActiveGame();
        if (game.getPlayer1().getCash() < 0 || game.getPlayer2().getCash() < 0) {
            return game.getPlayer1().getCash() > game.getPlayer2().getCash() ? game.getPlayer1().getName() : game.getPlayer2().getName();
        } else if (game.getCurrentTurn().equals(game.getPlayer1().getId()) && game.getPlayer1().getBoardPosition() == 0) {
            // Player 1 wins if they finish their turn on Start
            return game.getPlayer1().getName();
        } else {
            // No winner determined yet (or unexpected scenario)
            return null; // Indicate no winner found
        }
    }
}