package fr.kacetal.escalade.controllers;

import fr.kacetal.escalade.persistence.entities.Itinerary;
import fr.kacetal.escalade.persistence.entities.Sector;
import fr.kacetal.escalade.persistence.entities.util.Comment;
import fr.kacetal.escalade.persistence.entities.util.Grade;
import fr.kacetal.escalade.persistence.services.ItineraryService;
import fr.kacetal.escalade.persistence.services.SectorService;
import fr.kacetal.escalade.persistence.services.util.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

@Slf4j
@Controller
@RequestMapping("/itineraries")
public class ItineraryController {
    
    private static final String TEMPLATE_DIR = "itinerary";
    private static final String VIEW = TEMPLATE_DIR + "/view";
    private static final String LIST = TEMPLATE_DIR + "/list";
    private static final String UPDATE = TEMPLATE_DIR + "/update";
    private static final String NEW = TEMPLATE_DIR + "/new";
    
    @Value("${default.imagename}")
    private String defaultImageName;
    
    private final ItineraryService itineraryService;
    private final SectorService sectorService;
    private final StorageService storageService;
    
    public ItineraryController(ItineraryService itineraryService, SectorService sectorService, StorageService storageService) {
        this.itineraryService = itineraryService;
        this.sectorService = sectorService;
        this.storageService = storageService;
    }
    
    //READ all itineraries
    @GetMapping(path = {"view", "list", ""})
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
    public String postSector(Itinerary itinerary, @RequestParam("file") MultipartFile file) {
    
        String fileImageName = storageService.save(file);
    
        String imageName = selectImageName(itinerary.getImageName(), fileImageName);
    
        itinerary.setImageName(imageName);
    
        itinerary = itineraryService.save(itinerary);
        
        log.info("SAVE itinerary:\n{}", itinerary);
        
        return "redirect:/itineraries/view/" + itinerary.getId();
    }
    
    //DELETE itinerary by ID
    @GetMapping(value = "/delete/{id}")
    public String deleteSector(@PathVariable("id") Long id) {
        log.info("DELETE itinerary by ID : {}", id);
        
        itineraryService.delete(id);
        
        return "redirect:/itineraries/view";
    }
    
    private String selectImageName(String imageName, String fileImageName) {
        if (Objects.isNull(imageName) || imageName.isBlank()) {
            return defaultImageName;
        } else if (!defaultImageName.equals(fileImageName)) {
            return fileImageName;
        } else {
            return imageName;
        }
    }
}
