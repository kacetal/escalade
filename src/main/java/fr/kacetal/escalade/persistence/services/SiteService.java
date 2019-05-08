package fr.kacetal.escalade.persistence.services;

import fr.kacetal.escalade.persistence.entities.Site;

import java.util.List;

public interface SiteService {
    List<Site> findAll();
    
    List<Site> findSitesByName(String name);
    
    Site findById(Long id);
    
    Site save(Site site);
    
    void delete(Long id);
}
