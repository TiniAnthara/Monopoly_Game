package com.game.monopoly.model;

import org.springframework.data.annotation.Id;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "PlaceTable")
public class Place {

    @Id
    private String name; // Assuming place name is unique

    @ManyToOne
    private Player owner;

    private Integer rent;

    private Integer buy; // Cost for unclaimed places

    // Getters and Setters

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Player getOwner() {
		return owner;
	}

	public void setOwner(Player owner) {
		this.owner = owner;
	}

	public Integer getRent() {
		return rent;
	}

	public void setRent(Integer rent) {
		this.rent = rent;
	}

	public Integer getBuy() {
		return buy;
	}

	public void setBuy(Integer buy) {
		this.buy = buy;
	}

}

