package fr.kacetal.escalade.persistence.services;

import fr.kacetal.escalade.persistence.entities.Site;
import fr.kacetal.escalade.persistence.services.util.GenericService;

import java.util.Set;

public interface SiteService extends GenericService<Site> {
    Set<Site> findByCountry(String name);
    
    Set<Site> findByRegion(String name);
}