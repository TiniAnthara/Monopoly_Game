package com.game.monopoly.model;

import org.springframework.data.annotation.Id;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;

@Entity
@Table(name = "PlayerTable")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer cash;

    private String currentPosition;

    // Getters and Setters

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCash() {
		return cash;
	}

	public void setCash(Integer cash) {
		this.cash = cash;
	}

	public String getCurrentPosition() {
		return currentPosition;
	}

	public void setCurrentPosition(String currentPosition) {
		this.currentPosition = currentPosition;
	}

	public void setBoardPosition(int newPositionIndex) {
		// TODO Auto-generated method stub
		
	}

	public int getBoardPosition() {
		// TODO Auto-generated method stub
		return 0;
	}

    
}
