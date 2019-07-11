package fr.kacetal.escalade.persistence.services.impl.util;

import fr.kacetal.escalade.persistence.entities.util.Topo;
import fr.kacetal.escalade.persistence.repository.util.TopoRepository;
import fr.kacetal.escalade.persistence.services.util.TopoService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class TopoServiceImpl implements TopoService {
    
    private final TopoRepository topoRepository;
    
    public TopoServiceImpl(TopoRepository topoRepository) {
        this.topoRepository = topoRepository;
    }
    
    @Override
    public Set<Topo> findAll() {
        Set<Topo> topos = new HashSet<>();
        topoRepository.findAll().forEach(topos::add);
        return topos;
    }
    
    @Override
    public Set<Topo> findByName(String name) {
        return new HashSet<>(topoRepository.findToposByNameContainsIgnoreCase(name));
    }
    
    @Override
    public Topo findById(Long id) {
        return topoRepository.findById(id).orElse(null);
    }
    
    @Override
    public Topo save(Topo topo) {
        return topoRepository.save(topo);
    }
    
    @Override
    public void delete(Long id) {
        topoRepository.deleteById(id);
    }
}
