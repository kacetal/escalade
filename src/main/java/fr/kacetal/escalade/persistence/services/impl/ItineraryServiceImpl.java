package fr.kacetal.escalade.persistence.services.impl;

import fr.kacetal.escalade.persistence.entities.Itinerary;
import fr.kacetal.escalade.persistence.entities.util.Grade;
import fr.kacetal.escalade.persistence.repository.ItineraryRepository;
import fr.kacetal.escalade.persistence.services.ItineraryService;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.TreeSet;

@Service
public class ItineraryServiceImpl implements ItineraryService {
    
    private final ItineraryRepository itineraryRepository;
    
    public ItineraryServiceImpl(ItineraryRepository itineraryRepository) {
        this.itineraryRepository = itineraryRepository;
    }
    
    @Override
    public Set<Itinerary> findAll() {
        return new TreeSet<>(itineraryRepository.findAll());
    }
    
    @Override
    public Set<Itinerary> findByName(String name) {
        if ("".equals(name)) {
            return findAll();
        }
        return itineraryRepository.findItinerariesByNameContainingIgnoreCase(name);
    }
    
    @Override
    public Set<Itinerary> findBySpit(String spit) {
        if ("".equals(spit)) {
            return findAll();
        }
        return new TreeSet<>(itineraryRepository.findItinerariesBySpitContainingIgnoreCase(spit));
    }
    
    @Override
    public Set<Itinerary> findByHeight(Integer height) {
        if (height == null) {
            return findAll();
        }
        return new TreeSet<>(itineraryRepository.findItinerariesByHeight(height));
    }
    
    @Override
    public Set<Itinerary> findByNumberOfParts(Integer numberOfParts) {
        if (numberOfParts == null) {
            return findAll();
        }
        return new TreeSet<>(itineraryRepository.findItinerariesByNumberOfParts(numberOfParts));
    }
    
    @Override
    public Set<Itinerary> findByGrade(Grade grade) {
        if (grade == null) {
            return findAll();
        }
        return new TreeSet<>(itineraryRepository.findItinerariesByGrade(grade));
    }
    
    @Override
    public Itinerary findById(Long id) {
        return itineraryRepository.findById(id).orElse(null);
    }
    
    @Override
    public Set<Itinerary> findBySectorId(Long sectorId) {
        if (sectorId == null) {
            return new TreeSet<>();
        }
        return new TreeSet<>(itineraryRepository.findItinerariesBySectorId(sectorId));
    }
    
    @Override
    public Set<Itinerary> findBySiteId(Long siteId) {
        if (siteId == null) {
            return new TreeSet<>();
        }
        return new TreeSet<>(itineraryRepository.findItinerariesBySiteId(siteId));
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