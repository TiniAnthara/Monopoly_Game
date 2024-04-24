package com.game.monopoly.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.game.monopoly.model.Place;

@Repository
public interface PlaceRepository extends JpaRepository<Place, String> {

    List<String> getPlaceNames(); // Custom query to retrieve place names
}
