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
    
    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "sector_id")
    private Sector sector;
    
    @Enumerated(value = EnumType.STRING)
    private Grade grade;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String spit;
    
    @Column(nullable = false)
    private Integer numberOfParts;
    
    @Column(nullable = false)
    private Integer height;
    
    @Column(length = 1000)
    private String description;
    
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
                .add("grade=" + grade)
                .add("height=" + height)
                .add("spit='" + spit + "'")
                .add("numberOfParts=" + numberOfParts)
                .add("description='" + description + "'")
                .add("sectorName='" + sector.getName() + "'")
                .toString();
    }
}