package fr.kacetal.escalade.persistence.repository;

import fr.kacetal.escalade.persistence.entities.Site;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface SiteRepository extends JpaRepository<Site, Long> {
    Set<Site> findSitesByNameContainingIgnoreCase(String name);
    Set<Site> findSitesByRegionContainingIgnoreCase(String region);
    Set<Site> findSitesByCountryContainingIgnoreCase(String country);
}
