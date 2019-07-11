package fr.kacetal.escalade.persistence.services.impl;

import fr.kacetal.escalade.persistence.entities.Sector;
import fr.kacetal.escalade.persistence.repository.SectorRepository;
import fr.kacetal.escalade.persistence.services.SectorService;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.TreeSet;

@Service
public class SectorServiceImpl implements SectorService {
    
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
        return new TreeSet<>(sectorRepository.findSectorsByNameContainingIgnoreCase(name));
    }
    
    @Override
    public Set<Sector> findBySiteId(Long siteId) {
        if (siteId == null) {
            return new TreeSet<>();
        }
        return new TreeSet<>(sectorRepository.findSectorsBySiteId(siteId));
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
}
