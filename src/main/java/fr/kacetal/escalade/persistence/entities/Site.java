package fr.kacetal.escalade.persistence.entities;

import lombok.Data;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

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
    
    @OneToMany(mappedBy = "site", fetch = FetchType.EAGER)
    @Cascade(value = {org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.DELETE})
    private List<Sector> sectors;
}
