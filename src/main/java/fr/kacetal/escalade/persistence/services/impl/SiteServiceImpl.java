package fr.kacetal.escalade.persistence.services.impl;

import fr.kacetal.escalade.persistence.entities.Site;
import fr.kacetal.escalade.persistence.repository.SiteRepository;
import fr.kacetal.escalade.persistence.services.SiteService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

@Service
public class SiteServiceImpl implements SiteService {
    
    private static final Set<Site> EMPTY_SET = Collections.emptySet();
    
    @Value("${search.all.results}")
    private String ALL_RESULTS;
    
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
        if (name.isBlank() || ALL_RESULTS.equals(name)) {
            return nameBlancOrAll(name);
        }
        return new TreeSet<>(siteRepository.findSitesByNameContainingIgnoreCase(name));
    }
    
    @Override
    public Set<Site> findByCountry(String countryName) {
        if (countryName.isBlank() || ALL_RESULTS.equals(countryName)) {
            return nameBlancOrAll(countryName);
        }
        return new TreeSet<>(siteRepository.findSitesByCountryContainingIgnoreCase(countryName));
    }
    
    @Override
    public Set<Site> findByRegion(String regionName) {
        if (regionName.isBlank() || ALL_RESULTS.equals(regionName)) {
            return nameBlancOrAll(regionName);
        }
        return new TreeSet<>(siteRepository.findSitesByRegionContainingIgnoreCase(regionName));
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
    
    private Set<Site> nameBlancOrAll(String name) {
        if (name.isBlank()) {
            return null;
        }
        return findAll();
    }
}
