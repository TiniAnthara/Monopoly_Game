package com.game.monopoly.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.game.monopoly.model.Place;

@Service
public class PlaceServiceImpl implements PlaceService {

    // Assuming a fixed set of place names and properties (rent, cost)
    private static final String[] PLACE_NAMES = {"GO", "Old Kent Road", "Whitechapel Road", "King's Cross station ", "The Angel Islington", "Euston Road", 
                                                   "Pentonville Road ", "Pall Mall ", "Whitehall ", "Northumberland Avenue", "Marylebone station"};
    private static final int[] BUY = {60,60, 200, 100, 100, 120, 140, 140, 160, 200};
    private static final int[] RENTS = {30, 30, 100, 50, 50, 60, 70, 70, 80, 100};

    @Override
    public List<Place> createBoard() {
        List<Place> board = new ArrayList<>();
        for (int i = 0; i < PLACE_NAMES.length; i++) {
            Place place = new Place();
            place.setName(PLACE_NAMES[i]);
            place.setBuy(BUY[i]);
            place.setRent(RENTS[i]);

            board.add(place);
        }
        return board;
    }

    @Override
    public List<String> getPlaceNames() {
        return Arrays.asList(PLACE_NAMES);
    }

    @Override
    public Place getPlaceByName(String name) {
        for (Place place : createBoard()) {
            if (place.getName().equals(name)) {
                return place;
            }
        }
        return null;
    }
}
