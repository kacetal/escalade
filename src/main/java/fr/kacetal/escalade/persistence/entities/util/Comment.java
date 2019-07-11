package fr.kacetal.escalade.persistence.entities.util;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Size;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.StringJoiner;

import static javax.persistence.GenerationType.AUTO;

@Data
@Entity
public class Comment implements Comparable<Comment> {
    
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;
    
    @Size(min = 1, max = 50)
    @Column(nullable = false, length = 50)
    private String userName;
    
    @Size(min = 1, max = 1000)
    @Column(nullable = false, length = 1000)
    private String text;
    
    @Column(name = "created_on")
    private ZonedDateTime createdOn;
    
    @Override
    public int compareTo(Comment comment) {
        if (comment == null) return -1;
        return createdOn.compareTo(comment.getCreatedOn());
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return id.equals(comment.id) &&
                userName.equals(comment.userName) &&
                text.equals(comment.text) &&
                createdOn.equals(comment.createdOn);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, userName, text, createdOn);
    }
    
    @Override
    public String toString() {
        return new StringJoiner(", ", Comment.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("userName='" + userName + "'")
                .add("text='" + text + "'")
                .add("createdOn=" + createdOn)
                .toString();
    }
}
