package fr.kacetal.escalade.persistence.repository;

import fr.kacetal.escalade.persistence.entities.Sector;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface SectorRepository extends JpaRepository<Sector, Long> {
    Set<Sector> findSectorsByNameContainingIgnoreCase(String name);
    
    Set<Sector> findSectorsBySiteId(Long id);
}
