package fr.kacetal.escalade.controllers;

import fr.kacetal.escalade.persistence.entities.Comment;
import fr.kacetal.escalade.persistence.entities.Grade;
import fr.kacetal.escalade.persistence.entities.Itinerary;
import fr.kacetal.escalade.persistence.entities.Sector;
import fr.kacetal.escalade.persistence.services.ItineraryService;
import fr.kacetal.escalade.persistence.services.SectorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

@Slf4j
@Controller
@RequestMapping("/itineraries")
public class ItineraryController {
    
    private static final String VIEW = "itinerary/view";
    private static final String LIST = "itinerary/list";
    private static final String UPDATE = "itinerary/update";
    private static final String NEW = "itinerary/new";
    
    private final ItineraryService itineraryService;
    
    private final SectorService sectorService;
    
    public ItineraryController(ItineraryService itineraryService, SectorService sectorService) {
        this.itineraryService = itineraryService;
        this.sectorService = sectorService;
    }
    
    //READ all itineraries
    @GetMapping(path = {"", "view"})
    public String list(Model model) {
        log.info("READ all itineraries");
        
        Set<Itinerary> itineraries = itineraryService.findAll();
        
        model.addAttribute("itineraries", itineraries);
        
        log.info("No. of sectors: {}", itineraries.size());
        
        return LIST;
    }
    
    //READ itinerary by ID
    @GetMapping(path = "/view/{id}")
    public String showById(@PathVariable("id") Long id, Model model) {
        log.info("READ itinerary by ID : {}", id);
        
        Itinerary itinerary = itineraryService.findById(id);
        
        if (Objects.isNull(itinerary)) {
            log.info("Any sector found with ID : {}", id);
            return "redirect:/itineraries/view";
        }
        
        TreeSet<Comment> comments = new TreeSet<>(itinerary.getComments());
        
        model.addAttribute("itinerary", itinerary);
        model.addAttribute("comment", new Comment());
        model.addAttribute("comments", comments);
        
        return VIEW;
    }
    
    //UPDATE itinerary by ID
    @GetMapping(value = "/update/{id}")
    public String updateForm(@PathVariable("id") Long id, Model model) {
        log.info("UPDATE itinerary by ID : {}", id);
        
        Itinerary itinerary = itineraryService.findById(id);
        
        Set<Sector> sectors = sectorService.findAll();
        
        if (Objects.isNull(itinerary)) {
            log.info("Any itinerary found with ID : {}", id);
            return "redirect:/itineraries/";
        }
        
        model.addAttribute("grades", Grade.values());
        model.addAttribute("itinerary", itinerary);
        model.addAttribute("sectors", sectors);
        
        return UPDATE;
    }
    
    //CREATE new itinerary
    @GetMapping(value = "/new")
    public String createForm(Model model) {
        log.info("CREATE new itinerary");
        
        model.addAttribute("grades", Grade.values());
        model.addAttribute("itinerary", new Itinerary());
        model.addAttribute("sectors", sectorService.findAll());
        
        return NEW;
    }
    
    //CREATE new itinerary, POST from front
    @PostMapping
    public String postSector(Itinerary itinerary) {
        
        Itinerary savedItinerary = itineraryService.save(itinerary);
        
        log.info("SAVE itinerary:\n{}", savedItinerary);
        
        return "redirect:/itineraries/view/" + savedItinerary.getId();
    }
    
    //DELETE itinerary by ID
    @GetMapping(value = "/delete/{id}")
    public String deleteSector(@PathVariable("id") Long id) {
        log.info("DELETE itinerary by ID : {}", id);
        
        itineraryService.delete(id);
        
        return "redirect:/itineraries/view";
    }
    
    
}
