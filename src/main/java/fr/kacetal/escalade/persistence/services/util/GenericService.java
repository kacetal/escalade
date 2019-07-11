package fr.kacetal.escalade.persistence.services.util;

import java.util.Set;

public interface GenericService<T> {
    Set<T> findAll();
    
    Set<T> findByName(String name);
    
    T findById(Long id);
    
    T save(T site);
    
    void delete(Long id);
}
