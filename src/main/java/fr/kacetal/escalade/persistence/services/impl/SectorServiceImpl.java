package fr.kacetal.escalade.persistence.services.impl;

import fr.kacetal.escalade.persistence.entities.Sector;
import fr.kacetal.escalade.persistence.repository.SectorRepository;
import fr.kacetal.escalade.persistence.services.SectorService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

@Service
public class SectorServiceImpl implements SectorService {
    
    private static final Set<Sector> EMPTY_SET = Collections.emptySet();
    
    @Value("${search.all.results}")
    private String ALL_RESULTS;
    
    private final SectorRepository sectorRepository;
    
    public SectorServiceImpl(SectorRepository sectorRepository) {
        this.sectorRepository = sectorRepository;
    }
    
    @Override
    public Set<Sector> findAll() {
        return new TreeSet<>(sectorRepository.findAll());
    }
    
    @Override
    public Set<Sector> findByName(String name) {
        if (name.isBlank() || ALL_RESULTS.equals(name)) {
            return nameBlancOrAll(name);
        }
        return new TreeSet<>(sectorRepository.findSectorsByNameContainingIgnoreCase(name));
    }
    
    @Override
    public Set<Sector> findBySiteName(String siteName) {
        if (siteName.isBlank() || ALL_RESULTS.equals(siteName)) {
            return nameBlancOrAll(siteName);
        }
        return new TreeSet<>(sectorRepository.findSectorsBySiteNameContainingIgnoreCase(siteName));
    }
    
    @Override
    public Sector findById(Long id) {
        return sectorRepository.findById(id).orElse(null);
    }
    
    @Override
    public Sector save(Sector sector) {
        return sectorRepository.save(sector);
    }
    
    @Override
    public void delete(Long id) {
        sectorRepository.deleteById(id);
    }
    
    private Set<Sector> nameBlancOrAll(String name) {
        if (name.isBlank()) {
            return null;
        }
        return findAll();
    }
}
