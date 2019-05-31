package fr.kacetal.escalade.persistence.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

import static javax.persistence.CascadeType.REFRESH;
import static javax.persistence.CascadeType.REMOVE;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.AUTO;

@Data
@Entity
public class Sector {
    
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
    
    @OneToMany(
            mappedBy = "sector",
            fetch = EAGER,
            cascade = {REFRESH, REMOVE})
    private List<Itinerary> itineraries;
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sector sector = (Sector) o;
        return id.equals(sector.id) &&
                site.equals(sector.site) &&
                name.equals(sector.name) &&
                Objects.equals(description, sector.description);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, site, name, description);
    }
    
    @Override
    public String toString() {
        return new StringJoiner(", ", Sector.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("name='" + name + "'")
                .add("description='" + description + "'")
                .add("siteName='" + site.getName() + "'")
                .add("NmbrOfItineraries=" + (itineraries == null ? 0 : itineraries.size()))
                .toString();
    }
}
