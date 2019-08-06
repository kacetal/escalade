package fr.kacetal.escalade.persistence.entities.util;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Objects;
import java.util.StringJoiner;

@Data
@Entity
public class Reservation implements Comparable<Reservation> {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Size(min = 2, max = 50)
    @Column(nullable = false, length = 50)
    private String client;
    
    @Column(nullable = false)
    private LocalDate reservationDate;
    
    @Override
    public int compareTo(Reservation reservation) {
        return this.reservationDate.compareTo(reservation.getReservationDate());
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return client.equals(that.client) &&
                reservationDate.equals(that.reservationDate);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(client, reservationDate);
    }
    
    @Override
    public String toString() {
        return new StringJoiner(", ", Reservation.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("client='" + client + "'")
                .add("reservationDate=" + reservationDate)
                .toString();
    }
}
