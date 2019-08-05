package fr.kacetal.escalade.persistence.entities;

import fr.kacetal.escalade.persistence.entities.util.Comment;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;
import java.util.TreeSet;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.AUTO;

@Data
@Entity
public class Site implements Comparable<Site> {
    
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;
    
    @Size(min = 2, max = 255)
    @Column(nullable = false)
    private String name;
    
    @Size(min = 2, max = 255)
    private String country;
    
    @Size(min = 2, max = 255)
    private String region;
    
    @Size(max = 1000)
    @Column(length = 1000)
    private String description;
    
    @OneToMany(
            mappedBy = "site",
            fetch = EAGER,
            cascade = {REFRESH, REMOVE})
    private Set<Topo> topos = new TreeSet<>();
    
    @OneToMany(
            mappedBy = "site",
            fetch = EAGER,
            cascade = {REFRESH, REMOVE})
    private Set<Sector> sectors = new TreeSet<>();
    
    @OneToMany(
            orphanRemoval = true,
            fetch = EAGER,
            cascade = ALL)
    private Set<Comment> comments = new TreeSet<>();
    
    @Column(name = "image_name")
    private String imageName;
    
    public String getShortDescription(int nmbrOfChar) {
        if (description.length() > nmbrOfChar - 3) {
            return description.substring(0, nmbrOfChar) + "...";
        } else {
            return description;
        }
    }
    
    @Override
    public int compareTo(Site site) {
        if (site == null) return -1;
        return Long.compare(this.id, site.getId());
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Site site = (Site) o;
        return id.equals(site.id) &&
                name.equals(site.name) &&
                country.equals(site.country) &&
                region.equals(site.region) &&
                Objects.equals(description, site.description);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, name, country, region, description);
    }
    
    @Override
    public String toString() {
        return new StringJoiner(", ", Site.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("name='" + name + "'")
                .add("country='" + country + "'")
                .add("region='" + region + "'")
                .add("description='" + description + "'")
                .add("topos=" + topos.size())
                .add("sectors=" + sectors.size())
                .add("comments=" + comments.size())
                .add("imageName='" + imageName + "'")
                .toString();
    }
}
