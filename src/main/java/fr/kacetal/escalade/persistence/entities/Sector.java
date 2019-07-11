package fr.kacetal.escalade.persistence.entities;

import fr.kacetal.escalade.persistence.entities.util.Comment;
import lombok.Data;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;
import java.util.TreeSet;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.AUTO;

@Data
@Entity
public class Sector implements Comparable<Sector> {
    
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(length = 1000)
    private String description;
    
    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "site_id")
    private Site site;
    
    @Column(name = "image_name")
    private String imageName;
    
    @OneToMany(
            mappedBy = "sector",
            fetch = EAGER,
            cascade = {REFRESH, REMOVE})
    private Set<Itinerary> itineraries = new TreeSet<>();
    
    @OneToMany(
            orphanRemoval = true,
            fetch = EAGER,
            cascade = ALL)
    private Set<Comment> comments = new TreeSet<>();
    
    public String getShortDescription(int nmbrOfChar) {
        if (description.length() > nmbrOfChar - 3) {
            return description.substring(0, nmbrOfChar) + "...";
        } else {
            return description;
        }
    }
    
    @Override
    public int compareTo(Sector sector) {
        if (sector == null) return -1;
        return Long.compare(this.id, sector.getId());
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sector sector = (Sector) o;
        return id.equals(sector.id) &&
                name.equals(sector.name) &&
                Objects.equals(description, sector.description) &&
                site.equals(sector.site);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, site);
    }
    
    @Override
    public String toString() {
        return new StringJoiner(", ", Sector.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("name='" + name + "'")
                .add("description='" + description + "'")
                .add("site='" + site.getName() + "'")
                .add("nmbrOfItineraries=" + itineraries.size())
                .add("nmbrOfComments=" + comments.size())
                .add("imageName='" + imageName + "'")
                .toString();
    }
}
