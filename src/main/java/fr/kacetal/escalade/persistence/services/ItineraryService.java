package fr.kacetal.escalade.persistence.services;

import fr.kacetal.escalade.persistence.entities.Itinerary;
import fr.kacetal.escalade.persistence.entities.Sector;
import fr.kacetal.escalade.persistence.entities.Site;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ItineraryService {
    List<Itinerary> findAll();
    
    Itinerary findById(Long id);
    
    List<Itinerary> findByName(String name);
    
    List<Itinerary> findBySector(Sector sector);
    
    List<Itinerary> findBySite(Site site);
    
    Itinerary save(Itinerary itinerary);
    
    void delete(Long id);
}
