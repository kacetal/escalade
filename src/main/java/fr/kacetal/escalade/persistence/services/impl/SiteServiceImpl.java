package fr.kacetal.escalade.persistence.services.impl;

import fr.kacetal.escalade.persistence.entities.Site;
import fr.kacetal.escalade.persistence.repository.SiteRepository;
import fr.kacetal.escalade.persistence.services.SiteService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class SiteServiceImpl implements SiteService {
    
    private final SiteRepository siteRepository;
    
    public SiteServiceImpl(SiteRepository siteRepository) {
        this.siteRepository = siteRepository;
    }
    
    @Override
    public Set<Site> findAll() {
        Set<Site> sites = new HashSet<>();
        siteRepository.findAll().forEach(sites::add);
        return sites;
    }
    
    @Override
    public Set<Site> findByName(String name) {
        return new HashSet<>(siteRepository.findSitesByNameContainingIgnoreCase(name));
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
