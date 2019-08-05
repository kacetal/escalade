package fr.kacetal.escalade.controllers.util;

import fr.kacetal.escalade.persistence.entities.Topo;
import fr.kacetal.escalade.persistence.services.util.TopoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Objects;

@Slf4j
@Controller
@RequestMapping(path = "/reservations")
public class ReservationController {
    
    private static final String TEMPLATE_DIR = "topo";
    private static final String VIEW = TEMPLATE_DIR + "/view/";
    
    private final TopoService topoService;
    
    public ReservationController(TopoService topoService) {
        this.topoService = topoService;
    }
    
    @GetMapping(path = "/cancel/{topoId}/{resId}")
    public String cancelReservation(@PathVariable("topoId") final Long topoId,
                                    @PathVariable("topoId") final Long resId) {
    
        Topo topo = topoService.findById(topoId);
    
        if (Objects.isNull(topo)) {
            return VIEW;
        }
        
        topo.getReservations().removeIf(res -> res.getId().equals(resId));
    
        return VIEW + topoId;
    }
}
