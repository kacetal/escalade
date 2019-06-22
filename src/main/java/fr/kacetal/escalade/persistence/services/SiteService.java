package fr.kacetal.escalade.persistence.services;

import fr.kacetal.escalade.persistence.entities.Site;

import java.util.Set;

public interface SiteService {
    Set<Site> findAll();
    
    Set<Site> findByName(String name);
    
    Site findById(Long id);
    
    Site save(Site site);
    
    void delete(Long id);
}