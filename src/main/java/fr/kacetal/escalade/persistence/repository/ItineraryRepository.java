package fr.kacetal.escalade.persistence.repository;

import fr.kacetal.escalade.persistence.entities.Itinerary;
import fr.kacetal.escalade.persistence.entities.Sector;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface ItineraryRepository extends CrudRepository<Itinerary, Long> {
    Set<Itinerary> findItinerariesByNameContainsIgnoreCase(String name);
    
    Set<Itinerary> findItinerariesBySector(Sector sector);
    
    @Query(value = "SELECT itinerary.* FROM itinerary INNER JOIN sector ON itinerary.sector_id = sector.id WHERE sector.site_id = ?1",
            nativeQuery = true)
    Set<Itinerary> findBySiteId(Long id);
}
