package fr.kacetal.escalade.controllers.util;

import fr.kacetal.escalade.persistence.entities.Topo;
import fr.kacetal.escalade.persistence.entities.util.Reservation;
import fr.kacetal.escalade.persistence.services.util.TopoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Slf4j
@Controller
@RequestMapping(path = "/reservations")
public class ReservationController {
    
    private static final String TEMPLATE_DIR = "topo";
    private static final String TOPO_VIEW = TEMPLATE_DIR + "/view";
    private static final String RESERVATION = TEMPLATE_DIR + "/reservation";
    
    private final TopoService topoService;
    
    public ReservationController(TopoService topoService) {
        this.topoService = topoService;
    }
    
    @GetMapping(path = "/view/{topoId}")
    public String viewReservations(
            Model model,
            @PathVariable("topoId") final Long topoId) {
        Topo topo = topoService.findById(topoId);
        
        Set<Reservation> reservations = topo.getReservations();
        
        model.addAttribute("topo", topo);
        model.addAttribute("reservations", reservations);
        model.addAttribute("msgError", null);
        
        return RESERVATION;
    }
    
    @GetMapping(path = "/add")
    public String addReservation(Model model,
                                 @RequestParam(value = "topoId", defaultValue = "", required = false) String topoId,
                                 @RequestParam(value = "client", defaultValue = "", required = false) String client,
                                 @RequestParam(value = "from", defaultValue = "", required = false) String strFromDate,
                                 @RequestParam(value = "to", defaultValue = "", required = false) String strToDate
    ) {
        Topo topo = topoService.findById(parseLong(topoId));
        if (isNull(topo)) {
            return "redirect:/topos/view/";
        }
        model.addAttribute("topo", topo);
        
        LocalDate fromDate = parseDate(strFromDate);
        if (isNull(fromDate)) {
            String msgError = "Initial date is incorrect";
            model.addAttribute("msgError", msgError);
            return RESERVATION;
        }
        
        LocalDate toDate = parseDate(strToDate);
        if (isNull(toDate) || toDate.isBefore(fromDate)) {
            toDate = fromDate;
        }
    
        Set<Reservation> newReservations = fromDate.datesUntil(toDate.plusDays(1))
                .map(date -> {
                    Reservation reservation = new Reservation();
                    reservation.setClient(client);
                    reservation.setReservationDate(date);
                    return reservation;
                })
                .collect(Collectors.toSet());
    
        Set<Reservation> oldReservations = topo.getReservations();
    
        HashSet<LocalDate> crossRes = new HashSet<>();
        for (Reservation newReservation : newReservations) {
            for (Reservation oldReservation : oldReservations) {
                if (oldReservation.getReservationDate().equals(newReservation.getReservationDate())) {
                    crossRes.add(newReservation.getReservationDate());
                }
            }
        }
    
        if (!crossRes.isEmpty()) {
            StringJoiner dateJoiner = new StringJoiner(" ; ", "Dates are occupied: [ ", " ]");
            crossRes.forEach(localDate -> dateJoiner.add(localDate.toString()));
            String msgError = dateJoiner.toString();
            model.addAttribute("msgError", msgError);
            return RESERVATION;
        }
        
        topo.setReservations(newReservations);
        
        topoService.save(topo);
    
        model.addAttribute("topo", topo);
    
        return RESERVATION;
    }
    
    @GetMapping(path = "/cancel/{topoId}/{resId}")
    public String cancelReservation(@PathVariable("topoId") final Long topoId,
                                    @PathVariable("resId") final Long resId) {
        Topo topo = topoService.findById(topoId);
        if (isNull(topo)) {
            return "redirect:/topos/view/";
        }
        
        topo.getReservations().removeIf(res -> res.getId().equals(resId));
        topoService.save(topo);
        return "redirect:/reservations/view/" + topoId;
    }
    
    private Long parseLong(final String strLong) {
        if (strLong.matches("[+-]?\\d+")) {
            try {
                return Long.valueOf(strLong);
            } catch (NumberFormatException e) {
                log.warn("The string '{}' for parsing is not a correct number", strLong);
                log.warn("NumberFormatException:", e);
            }
        } else {
            log.warn("The string '{}' for parsing is not a correct number", strLong);
        }
        return -1L;
    }
    
    private LocalDate parseDate(final String date) {
        if (isNull(date) || date.isBlank()) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("[dd/MM/yyyy]");
        return LocalDate.parse(date, formatter);
    }
}
