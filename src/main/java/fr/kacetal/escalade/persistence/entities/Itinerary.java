package fr.kacetal.escalade.persistence.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Data
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