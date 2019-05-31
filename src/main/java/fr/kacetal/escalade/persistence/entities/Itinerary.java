package fr.kacetal.escalade.persistence.entities;

import lombok.Data;

import javax.persistence.*;

import java.util.Objects;
import java.util.StringJoiner;

import static javax.persistence.FetchType.*;

@Data
@Entity
public class Itinerary {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private Grade grade;
    
    @Column(nullable = false)
    private Integer height;
    
    @Column(nullable = false)
    private Integer numberOfParts;
    
    @Column(nullable = false)
    private String spit;
    
    @Column(length = 1000)
    private String description;
    
    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "sector_id", nullable = false)
    private Sector sector;
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Itinerary itinerary = (Itinerary) o;
        return id.equals(itinerary.id) &&
                sector.equals(itinerary.sector) &&
                grade == itinerary.grade &&
                name.equals(itinerary.name) &&
                spit.equals(itinerary.spit) &&
                numberOfParts.equals(itinerary.numberOfParts) &&
                height.equals(itinerary.height) &&
                Objects.equals(description, itinerary.description);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, sector, grade, name, spit, numberOfParts, height, description);
    }
    
    @Override
    public String toString() {
        return new StringJoiner(", ", Itinerary.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("name='" + name + "'")
                .add("grade='" + (grade == null ? "null" : grade.toString()) + "'")
                .add("height=" + height)
                .add("numberOfParts=" + numberOfParts)
                .add("spit='" + spit + "'")
                .add("description='" + description + "'")
                .add("sectorName='" + (sector == null ? "null" : sector.getName()) + "'")
                .toString();
    }
}