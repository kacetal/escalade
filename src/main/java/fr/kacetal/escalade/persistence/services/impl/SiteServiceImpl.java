package fr.kacetal.escalade.persistence.services.impl;

import fr.kacetal.escalade.persistence.entities.Site;
import fr.kacetal.escalade.persistence.repository.SiteRepository;
import fr.kacetal.escalade.persistence.services.SiteService;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.TreeSet;

@Service
public class SiteServiceImpl implements SiteService {
    
    private final SiteRepository siteRepository;
    
    public SiteServiceImpl(SiteRepository siteRepository) {
        this.siteRepository = siteRepository;
    }
    
    @Override
    public Set<Site> findAll() {
        return new TreeSet<>(siteRepository.findAll());
    }
    
    @Override
    public Set<Site> findByName(String name) {
        if ("".equals(name)) {
            return findAll();
        } else {
            return new TreeSet<>(siteRepository.findSitesByNameContainingIgnoreCase(name));
        }
    }
    
    @Override
    public Set<Site> findByCountry(String name) {
        if ("".equals(name)) {
            return findAll();
        } else {
            return new TreeSet<>(siteRepository.findSitesByCountryContainingIgnoreCase(name));
        }
    }
    
    @Override
    public Set<Site> findByRegion(String name) {
        if ("".equals(name)) {
            return findAll();
        } else {
            return new TreeSet<>(siteRepository.findSitesByRegionContainingIgnoreCase(name));
        }
    }
    
    @Override
    public Site findById(Long id) {
        return siteRepository.findById(id).orElse(null);
    }
    
    @Override
    public Site save(Site site) {
        return siteRepository.save(site);
    }
    
    @Override
    public void delete(Long id) {
        siteRepository.deleteById(id);
    }
}
