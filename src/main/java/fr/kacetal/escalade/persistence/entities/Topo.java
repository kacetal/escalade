package fr.kacetal.escalade.persistence.entities;


import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Objects;
import java.util.Set;
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
    
    @OneToMany(
            orphanRemoval = true,
            fetch = EAGER,
            cascade = ALL)
    @JoinColumn(name = "comment_id")
    private Set<Comment> comments = new TreeSet<>();
    
    @OneToMany(
            orphanRemoval = true,
            fetch = EAGER,
            cascade = ALL)
    @JoinColumn(name = "site_id")
    private Set<Site> sites = new TreeSet<>();
    
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
                description.equals(topo.description);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
    }
}
