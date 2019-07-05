package fr.kacetal.escalade.persistence.repository;

import fr.kacetal.escalade.persistence.entities.Topo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface TopoRepository extends JpaRepository<Topo, Long> {
    Set<Topo> findToposByNameContainsIgnoreCase(String name);
}
