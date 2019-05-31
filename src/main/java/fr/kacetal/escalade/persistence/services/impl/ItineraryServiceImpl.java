package fr.kacetal.escalade.persistence.services.impl;

import fr.kacetal.escalade.persistence.entities.Itinerary;
import fr.kacetal.escalade.persistence.entities.Sector;
import fr.kacetal.escalade.persistence.entities.Site;
import fr.kacetal.escalade.persistence.repository.ItineraryRepository;
import fr.kacetal.escalade.persistence.services.ItineraryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItineraryServiceImpl implements ItineraryService {
    
    private final ItineraryRepository itineraryRepository;
    
    public ItineraryServiceImpl(ItineraryRepository itineraryRepository) {
        this.itineraryRepository = itineraryRepository;
    }
    
    @Override
    public List<Itinerary> findAll() {
        List<Itinerary> itineraries = new ArrayList<>();
        itineraryRepository.findAll().forEach(itineraries::add);
        return itineraries;
    }
    
    @Override
    public List<Itinerary> findByName(String name) {
        return itineraryRepository.findItinerariesByNameContainsIgnoreCase(name);
    }
    
    @Override
    public List<Itinerary> findBySector(Sector sector) {
        return itineraryRepository.findItinerariesBySector(sector);
    }
    
    @Override
    public List<Itinerary> findBySite(Site site) {
        return itineraryRepository.findBySiteId(site.getId());
    }
    
    @Override
    public Itinerary findById(Long id) {
        return itineraryRepository.findById(id).orElse(null);
    }
    
    @Override
    public Itinerary save(Itinerary itinerary) {
        return itineraryRepository.save(itinerary);
    }
    
    @Override
    public void delete(Long id) {
        itineraryRepository.deleteById(id);
    }
}