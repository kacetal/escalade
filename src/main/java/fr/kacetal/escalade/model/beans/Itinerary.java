package fr.kacetal.escalade.model.beans;

import javax.persistence.*;

@Entity
public class Itinerary {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.EAGER)
    private Sector sector;
    
    @Enumerated(value = EnumType.STRING)
    private Grade grade;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private Integer length;
    
    @Column(length = 1000)
    private String description;
}