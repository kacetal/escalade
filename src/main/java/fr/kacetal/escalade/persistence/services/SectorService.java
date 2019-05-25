package fr.kacetal.escalade.persistence.services;

import fr.kacetal.escalade.persistence.entities.Sector;
import fr.kacetal.escalade.persistence.entities.Site;

import java.util.List;

public interface SectorService {
    List<Sector> findAll();
    
    List<Sector> findByName(String name);
    
    List<Sector> findBySite(Site site);
    
    Sector findById(Long id);
    
    Sector save(Sector sector);
    
    void delete(Long id);
}
