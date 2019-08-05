package fr.kacetal.escalade.persistence.services.impl.util;

import fr.kacetal.escalade.persistence.entities.Topo;
import fr.kacetal.escalade.persistence.repository.util.TopoRepository;
import fr.kacetal.escalade.persistence.services.util.TopoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

@Service
public class TopoServiceImpl implements TopoService {
    
    private static final Set<Topo> EMPTY_SET = Collections.emptySet();
    private final TopoRepository topoRepository;
    @Value("${search.all.results}")
    private String ALL_RESULTS;
    
    public TopoServiceImpl(TopoRepository topoRepository) {
        this.topoRepository = topoRepository;
    }
    
    @Override
    public Set<Topo> findAll() {
        return new TreeSet<>(topoRepository.findAll());
    }
    
    @Override
    public Set<Topo> findByName(String name) {
        if (name.isBlank() || ALL_RESULTS.equals(name)) {
            return nameBlancOrAll(name);
        }
        return new TreeSet<>(topoRepository.findToposByNameContainsIgnoreCase(name));
    }
    
    @Override
    public Set<Topo> findBySiteName(String siteName) {
        if (siteName.isBlank() || ALL_RESULTS.equals(siteName)) {
            return nameBlancOrAll(siteName);
        }
        return new TreeSet<>(topoRepository.findToposBySiteNameContainingIgnoreCase(siteName));
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
    
    private Set<Topo> nameBlancOrAll(String name) {
        if (name.isBlank()) {
            return null;
        }
        return findAll();
    }
}
