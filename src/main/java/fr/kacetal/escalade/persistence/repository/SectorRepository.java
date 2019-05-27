package fr.kacetal.escalade.persistence.repository;

import fr.kacetal.escalade.persistence.entities.Sector;
import fr.kacetal.escalade.persistence.entities.Site;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SectorRepository extends CrudRepository<Sector, Long> {
    List<Sector> findSectorsByNameContainsIgnoreCase(String name);
    List<Sector> findSectorsBySite(Site site);
}
