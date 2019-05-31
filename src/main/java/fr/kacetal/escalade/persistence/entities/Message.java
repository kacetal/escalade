package fr.kacetal.escalade.persistence.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Size;

import static javax.persistence.GenerationType.AUTO;

@Data
@Entity
public class Message {
    
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;
    
    @Size(min = 2, max = 50)
    @Column(nullable = false, length = 50)
    private String user;
    
    @Size(min = 1, max = 1000)
    @Column(nullable = false, length = 1000)
    private String text;
}
