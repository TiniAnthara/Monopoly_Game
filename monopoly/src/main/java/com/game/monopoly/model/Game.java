package com.game.monopoly.model;

import java.util.List;

import org.springframework.data.annotation.Id;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "GameTable")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private Boolean active;

    @ManyToOne
    private Player player1;

    @ManyToOne
    private Player player2;

    private Long currentTurn;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Place> board;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Player getPlayer1() {
		return player1;
	}

	public void setPlayer1(Player player1) {
		this.player1 = player1;
	}

	public Player getPlayer2() {
		return player2;
	}

	public void setPlayer2(Player player2) {
		this.player2 = player2;
	}

	public Long getCurrentTurn() {
		return currentTurn;
	}

	public void setCurrentTurn(Long long1) {
		this.currentTurn = long1;
	}

	public List<Place> getBoard() {
		return board;
	}

	public void setBoard(List<Place> board) {
		this.board = board;
	}

}
