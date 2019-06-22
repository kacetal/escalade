package fr.kacetal.escalade.persistence.services;

import fr.kacetal.escalade.persistence.entities.Sector;
import fr.kacetal.escalade.persistence.entities.Site;

import java.util.Set;

public interface SectorService {
    Set<Sector> findAll();
    
    Set<Sector> findByName(String name);
    
    Set<Sector> findBySite(Site site);
    
    Sector findById(Long id);
    
    Sector save(Sector sector);
    
    void delete(Long id);
}
