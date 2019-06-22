package fr.kacetal.escalade.persistence.services;

import fr.kacetal.escalade.persistence.entities.Itinerary;
import fr.kacetal.escalade.persistence.entities.Sector;
import fr.kacetal.escalade.persistence.entities.Site;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface ItineraryService {
    Set<Itinerary> findAll();
    
    Itinerary findById(Long id);
    
    Set<Itinerary> findByName(String name);
    
    Set<Itinerary> findBySector(Sector sector);
    
    Set<Itinerary> findBySite(Site site);
    
    Itinerary save(Itinerary itinerary);
    
    void delete(Long id);
}
