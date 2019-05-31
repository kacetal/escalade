package fr.kacetal.escalade.persistence.repository;

import fr.kacetal.escalade.persistence.entities.Itinerary;
import fr.kacetal.escalade.persistence.entities.Sector;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ItineraryRepository extends CrudRepository<Itinerary, Long> {
    List<Itinerary> findItinerariesByNameContainsIgnoreCase(String name);
    
    List<Itinerary> findItinerariesBySector(Sector sector);
    
    @Query(value = "SELECT itinerary.* FROM itinerary INNER JOIN sector ON itinerary.sector_id = sector.id WHERE sector.site_id = ?1",
            nativeQuery = true)
    List<Itinerary> findBySiteId(Long id);
}
