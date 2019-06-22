package fr.kacetal.escalade.persistence.services.impl;

import fr.kacetal.escalade.persistence.entities.Sector;
import fr.kacetal.escalade.persistence.entities.Site;
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
        Set<Sector> sectors = new TreeSet<>();
        sectorRepository.findAll().forEach(sectors::add);
        return sectors;
    }
    
    @Override
    public Set<Sector> findByName(String name) {
        return new TreeSet<>(sectorRepository.findSectorsByNameContainsIgnoreCase(name));
    }
    
    @Override
    public Set<Sector> findBySite(Site site) {
        return new TreeSet<>(sectorRepository.findSectorsBySite(site));
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
