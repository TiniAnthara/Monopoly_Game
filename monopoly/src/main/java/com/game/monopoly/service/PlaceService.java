package com.game.monopoly.service;

import java.util.List;

import com.game.monopoly.model.Place;

public interface PlaceService {

    List<Place> createBoard();

    List<String> getPlaceNames();

    Place getPlaceByName(String name);
}

