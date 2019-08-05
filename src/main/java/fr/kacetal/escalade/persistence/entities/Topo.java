package fr.kacetal.escalade.persistence.entities;


import com.fasterxml.jackson.annotation.JsonProperty;
import fr.kacetal.escalade.persistence.entities.util.Comment;
import fr.kacetal.escalade.persistence.entities.util.Reservation;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;
import java.util.TreeSet;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;

@Data
@Entity
public class Topo implements Comparable<Topo> {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Size(min = 2, max = 255)
    @Column(nullable = false)
    private String name;
    
    @Size(max = 1000)
    @Column(length = 1000)
    private String description;
    
    @Column(name = "image_name")
    private String imageName;
    
    @OneToMany(
            orphanRemoval = true,
            fetch = EAGER,
            cascade = ALL)
    private Set<Comment> comments = new TreeSet<>();
    
    @OneToMany(
            orphanRemoval = true,
            fetch = EAGER,
            cascade = ALL)
    @JsonProperty("reservations")
    private Set<Reservation> reservations = new TreeSet<>();
    
    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "site_id")
    private Site site;
    
    public String getShortDescription(int nmbrOfChar) {
        if (description.length() > nmbrOfChar - 3) {
            return description.substring(0, nmbrOfChar) + "...";
        } else {
            return description;
        }
    }
    
    @Override
    public int compareTo(Topo topo) {
        if (topo == null) return -1;
        return Long.compare(this.id, topo.getId());
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Topo topo = (Topo) o;
        return id.equals(topo.id) &&
                name.equals(topo.name) &&
                description.equals(topo.description) &&
                site.equals(topo.site);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, site);
    }
    
    @Override
    public String toString() {
        return new StringJoiner(", ", Topo.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("name='" + name + "'")
                .add("description='" + description + "'")
                .add("imageName='" + imageName + "'")
                .add("comments=" + comments.size())
                .add("reservations=" + reservations.size())
                .add("site=" + site.getName())
                .toString();
    }
}
