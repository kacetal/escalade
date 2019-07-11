package fr.kacetal.escalade.persistence.services;

import fr.kacetal.escalade.persistence.entities.Sector;
import fr.kacetal.escalade.persistence.services.util.GenericService;

import java.util.Set;

public interface SectorService extends GenericService<Sector> {
    Set<Sector> findBySiteId(Long siteId);
}
