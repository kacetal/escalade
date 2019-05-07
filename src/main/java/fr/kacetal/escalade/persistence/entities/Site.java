package fr.kacetal.escalade.persistence.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Entity
public class Site {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @NotNull()
    @Size(min = 2, max = 255)
    @Column(nullable = false)
    private String name;
    
    @Size(min = 2, max = 1000)
    @Column(length = 1000)
    private String description;
}
