package fr.kacetal.escalade.persistence.services.impl;

import fr.kacetal.escalade.persistence.entities.Itinerary;
import fr.kacetal.escalade.persistence.entities.util.Grade;
import fr.kacetal.escalade.persistence.repository.ItineraryRepository;
import fr.kacetal.escalade.persistence.services.ItineraryService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

@Service
public class ItineraryServiceImpl implements ItineraryService {
    
    @Value("${search.all.results}")
    private String ALL_RESULTS;
    
    private static final Set<Itinerary> EMPTY_SET = Collections.emptySet();
    
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
        if (name.isBlank() || ALL_RESULTS.equals(name)) {
            return nameBlancOrAll(name);
        }
        return itineraryRepository.findItinerariesByNameContainingIgnoreCase(name);
    }
    
    @Override
    public Set<Itinerary> findBySpit(String spit) {
        if (spit.isBlank() || ALL_RESULTS.equals(spit)) {
            return nameBlancOrAll(spit);
        }
        return new TreeSet<>(itineraryRepository.findItinerariesBySpitContainingIgnoreCase(spit));
    }
    
    @Override
    public Set<Itinerary> findByHeight(Integer height) {
        if (height < 0) {
            return EMPTY_SET;
        }
        return new TreeSet<>(itineraryRepository.findItinerariesByHeight(height));
    }
    
    @Override
    public Set<Itinerary> findByNumberOfParts(Integer numberOfParts) {
        if (numberOfParts < 0) {
            return EMPTY_SET;
        }
        return new TreeSet<>(itineraryRepository.findItinerariesByNumberOfParts(numberOfParts));
    }
    
    @Override
    public Set<Itinerary> findByGrade(Grade grade) {
        if (grade == Grade.EMPTY) {
            return null;
        } else if (grade == Grade.ALL) {
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
            return EMPTY_SET;
        }
        return new TreeSet<>(itineraryRepository.findItinerariesBySectorId(sectorId));
    }
    
    @Override
    public Set<Itinerary> findBySiteId(Long siteId) {
        if (siteId == null) {
            return EMPTY_SET;
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
    
    private Set<Itinerary> nameBlancOrAll(String name) {
        if (name.isBlank()) {
            return null;
        }
        return findAll();
    }
}