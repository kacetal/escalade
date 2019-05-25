package fr.kacetal.escalade.persistence.services.impl;

import fr.kacetal.escalade.persistence.entities.Sector;
import fr.kacetal.escalade.persistence.entities.Site;
import fr.kacetal.escalade.persistence.repository.SectorRepository;
import fr.kacetal.escalade.persistence.services.SectorService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SectorServiceImpl implements SectorService {
    
    private final SectorRepository sectorRepository;
    
    public SectorServiceImpl(SectorRepository sectorRepository) {
        this.sectorRepository = sectorRepository;
    }
    
    @Override
    public List<Sector> findAll() {
        List<Sector> sectors = new ArrayList<>();
        sectorRepository.findAll().forEach(sectors::add);
        return sectors;
    }
    
    @Override
    public List<Sector> findByName(String name) {
        return new ArrayList<>(sectorRepository.findSectorsByNameContainsIgnoreCase(name));
    }
    
    @Override
    public List<Sector> findBySite(Site site) {
        return new ArrayList<>(sectorRepository.findSectorsBySite(site));
    }
    
    @Override
    public Sector findById(Long id) {
        return null;
    }
    
    @Override
    public Sector save(Sector sector) {
        return null;
    }
    
    @Override
    public void delete(Long id) {
    
    }
}
