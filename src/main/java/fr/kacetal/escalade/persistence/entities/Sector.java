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
    /*
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "site_id")
    private Site site;
    */
    @OneToMany(mappedBy = "sector", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Itinerary> itinerary;
    
    @Column(nullable = false)
    private String name;
    
    @Column(length = 1000)
    private String description;
}
