package fr.kacetal.escalade.persistence.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity(name = "Sector")
public class Sector {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @ManyToOne(fetch = FetchType.EAGER)
    private Site site;
    
    @OneToMany(mappedBy = "sector", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Itinerary> itinerary;
    
    @Column(nullable = false)
    private String name;
    
    @Column(length = 1000)
    private String description;
}
