package fr.kacetal.escalade.persistence.services;

import fr.kacetal.escalade.persistence.entities.Itinerary;
import fr.kacetal.escalade.persistence.entities.util.Grade;
import fr.kacetal.escalade.persistence.services.util.GenericService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface ItineraryService extends GenericService<Itinerary> {
    
    Set<Itinerary> findBySectorId(Long sectorId);
    
    Set<Itinerary> findBySiteId(Long siteId);
    
    Set<Itinerary> findByGrade(Grade grade);
    
    Set<Itinerary> findBySpit(String spit);
    
    Set<Itinerary> findByHeight(Integer height);
    
    Set<Itinerary> findByNumberOfParts(Integer numberOfParts);
}
