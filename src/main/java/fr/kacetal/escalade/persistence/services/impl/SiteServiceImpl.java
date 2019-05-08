package fr.kacetal.escalade.persistence.services.impl;

import fr.kacetal.escalade.persistence.entities.Site;
import fr.kacetal.escalade.persistence.repository.SiteRepository;
import fr.kacetal.escalade.persistence.services.SiteService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SiteServiceImpl implements SiteService {
    
    private final SiteRepository siteRepository;
    
    public SiteServiceImpl(SiteRepository siteRepository) {
        this.siteRepository = siteRepository;
    }
    
    @Override
    public List<Site> findAll() {
        List<Site> sites = new ArrayList<>();
        siteRepository.findAll().forEach(sites::add);
        return sites;
    }
    
    @Override
    public List<Site> findSitesByName(String name) {
        return new ArrayList<>(siteRepository.findSitesByNameContainsIgnoreCase(name));
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
