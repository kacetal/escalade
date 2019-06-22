package fr.kacetal.escalade.persistence.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;
import java.util.TreeSet;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;

@Data
@Entity
public class Itinerary implements Comparable<Itinerary> {
    
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
    
    @OneToMany(
            orphanRemoval = true,
            fetch = EAGER,
            cascade = ALL)
    @JoinColumn(name = "comment_id")
    private Set<Comment> comments = new TreeSet<>();
    
    public String getShortDescription(int nmbrOfChar) {
        if (description.length() > nmbrOfChar - 3) {
            return description.substring(0, nmbrOfChar) + "...";
        } else {
            return description;
        }
    }
    
    @Override
    public int compareTo(Itinerary itinerary) {
        if (itinerary == null) return -1;
        return Long.compare(this.id, itinerary.getId());
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Itinerary itinerary = (Itinerary) o;
        return id.equals(itinerary.id) &&
                name.equals(itinerary.name) &&
                grade == itinerary.grade &&
                height.equals(itinerary.height) &&
                numberOfParts.equals(itinerary.numberOfParts) &&
                spit.equals(itinerary.spit) &&
                Objects.equals(description, itinerary.description) &&
                sector.equals(itinerary.sector);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, name, grade, height, numberOfParts, spit, description, sector);
    }
    
    @Override
    public String toString() {
        return new StringJoiner(", ", Itinerary.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("name='" + name + "'")
                .add("grade=" + grade)
                .add("height=" + height)
                .add("numberOfParts=" + numberOfParts)
                .add("spit='" + spit + "'")
                .add("description='" + description + "'")
                .add("sector='" + sector.getName() + "'")
                .add("nmbrOfComments=" + comments.size())
                .toString();
    }
}