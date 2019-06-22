package fr.kacetal.escalade.persistence.repository;

import fr.kacetal.escalade.persistence.entities.Site;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface SiteRepository extends CrudRepository<Site, Long> {
    Set<Site> findSitesByNameContainsIgnoreCase(String name);
}
