package fr.kacetal.escalade.persistence.entities.util;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;

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
}
