package fr.kacetal.escalade.persistence.services;

import fr.kacetal.escalade.persistence.entities.Topo;

import java.util.Set;

public interface TopoService {
    Set<Topo> findAll();
    
    Set<Topo> findByName(String name);
    
    Topo findById(Long id);
    
    Topo save(Topo topo);
    
    void delete(Long id);
}