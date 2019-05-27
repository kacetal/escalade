package fr.kacetal.escalade.persistence.repository;

import fr.kacetal.escalade.persistence.entities.Itinerary;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ItineraryRepository extends CrudRepository<Itinerary, Long> {
    List<Itinerary> findItinerariesByNameContainsIgnoreCase(String name);
}
