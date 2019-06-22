package fr.kacetal.escalade.persistence.repository;

import fr.kacetal.escalade.persistence.entities.Sector;
import fr.kacetal.escalade.persistence.entities.Site;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface SectorRepository extends CrudRepository<Sector, Long> {
    Set<Sector> findSectorsByNameContainsIgnoreCase(String name);
    Set<Sector> findSectorsBySite(Site site);
}
