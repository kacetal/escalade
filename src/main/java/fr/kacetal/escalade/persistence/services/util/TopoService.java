package fr.kacetal.escalade.persistence.services.util;

import fr.kacetal.escalade.persistence.entities.Topo;

import java.util.Set;

public interface TopoService extends GenericService<Topo> {
    Set<Topo> findBySiteName(String siteName);
}