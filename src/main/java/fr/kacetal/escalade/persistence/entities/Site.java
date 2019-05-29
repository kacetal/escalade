package fr.kacetal.escalade.persistence.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity(name = "Site")
public class Site {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Size(min = 2, max = 255)
    @Column(nullable = false)
    private String name;
    
    @Size(min = 2, max = 255)
    private String country;
    
    @Size(min = 2, max = 255)
    private String region;
    
    @Size(max = 1000)
    @Column(length = 1000)
    private String description;
    
    @OneToMany(mappedBy = "site", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Sector> sectors;
}
