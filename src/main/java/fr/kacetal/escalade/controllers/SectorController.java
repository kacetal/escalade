package fr.kacetal.escalade.controllers;

import fr.kacetal.escalade.persistence.entities.Comment;
import fr.kacetal.escalade.persistence.entities.Sector;
import fr.kacetal.escalade.persistence.entities.Site;
import fr.kacetal.escalade.persistence.services.SectorService;
import fr.kacetal.escalade.persistence.services.SiteService;
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
@RequestMapping("/sectors")
public class SectorController {
    
    private static final String VIEW = "sector/view";
    private static final String LIST = "sector/list";
    private static final String UPDATE = "sector/update";
    private static final String NEW = "sector/new";
    
    private final SectorService sectorService;
    
    private final SiteService siteService;
    
    public SectorController(SectorService sectorService, SiteService siteService) {
        this.sectorService = sectorService;
        this.siteService = siteService;
    }
    
    // READ all sectors
    @GetMapping(path = {"/view", ""})
    public String list(Model model) {
        
        log.info("READ all sectors");
        
        Set<Sector> sectors = sectorService.findAll();
        
        model.addAttribute("sectors", sectors);
        
        log.info("No. of sectors: {}", sectors.size());
        
        return LIST;
    }
    
    @GetMapping(path = "/view/{id}")
    public String showById(@PathVariable("id") Long id, Model model) {
        log.info("READ sector by ID : {}", id);
        
        Sector sector = sectorService.findById(id);
        
        if (Objects.isNull(sector)) {
            log.info("Any sector found with ID : {}", id);
            return "redirect:/sectors/";
        }
    
        TreeSet<Comment> comments = new TreeSet<>(sector.getComments());
        
        model.addAttribute("sector", sector);
        model.addAttribute("comment", new Comment());
        model.addAttribute("comments", comments);
        
        return VIEW;
    }
    /*
    //SEARCH sectors by Name
    @GetMapping(path = "/search", params = {"name"})
    public String showByName(@RequestParam("name") String name, Model model) {
        log.info("READ list of sectors by name : \"{}\"", name);
        
        List<Sector> sectors = sectorService.findByName(name);
        
        model.addAttribute("sectors", sectors);
        
        log.info("No. of sectors: {}", sectors.size());
        
        return LIST;
    }*/
    
    //UPDATE sector by ID
    @GetMapping(value = "/update/{id}")
    public String updateForm(@PathVariable("id") Long id, Model model) {
        log.info("UPDATE sector by ID : {}", id);
        
        Set<Site> sites = siteService.findAll();
        
        Sector sector = sectorService.findById(id);
        
        if (Objects.isNull(sector)) {
            log.info("Any sector found with ID : {}", id);
            return "redirect:/sectors/";
        }
        
        model.addAttribute("sites", sites);
        model.addAttribute("sector", sector);
        
        return UPDATE;
    }
    
    //CREATE new sector
    @GetMapping(value = "/new")
    public String createForm(Model model) {
        Sector sector = new Sector();
        Set<Site> sites = siteService.findAll();
        
        model.addAttribute("sites", sites);
        model.addAttribute("sector", sector);
        return NEW;
    }
    
    //CREATE new sector, POST from front
    @PostMapping
    public String postSector(Sector sector) {
        Sector saveSector = sectorService.save(sector);
    
        log.info("SAVE sector:\n{}", saveSector);
    
        return "redirect:/sectors/view/" + saveSector.getId();
    }
    
    //DELETE sector by ID
    @GetMapping(value = "/delete/{id}")
    public String deleteSector(@PathVariable("id") Long id) {
        log.info("DELETE sector by ID : {}", id);
        
        sectorService.delete(id);
        
        return "redirect:/sectors/view";
    }
}
