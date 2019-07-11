package fr.kacetal.escalade.persistence.repository;

import fr.kacetal.escalade.persistence.entities.Itinerary;
import fr.kacetal.escalade.persistence.entities.util.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface ItineraryRepository extends JpaRepository<Itinerary, Long> {
    Set<Itinerary> findItinerariesByNameContainingIgnoreCase(String name);
    
    Set<Itinerary> findItinerariesByGrade(Grade grade);
    
    Set<Itinerary> findItinerariesBySpitContainingIgnoreCase(String spit);
    
    Set<Itinerary> findItinerariesByHeight(Integer height);
    
    Set<Itinerary> findItinerariesByNumberOfParts(Integer numberOfParts);
    
    Set<Itinerary> findItinerariesBySectorId(Long sectorId);
    
    @Query(value = "SELECT itinerary.* FROM itinerary INNER JOIN sector ON itinerary.sector_id = sector.id WHERE sector.site_id = ?1",
            nativeQuery = true)
    Set<Itinerary> findItinerariesBySiteId(Long siteId);
}
