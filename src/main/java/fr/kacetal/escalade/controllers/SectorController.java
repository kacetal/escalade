package fr.kacetal.escalade.controllers;

import fr.kacetal.escalade.persistence.entities.Sector;
import fr.kacetal.escalade.persistence.entities.Site;
import fr.kacetal.escalade.persistence.services.SectorService;
import fr.kacetal.escalade.persistence.services.SiteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Slf4j
@Controller
@RequestMapping("/sectors")
public class SectorController {
    
    
    private static final String VIEW = "sector/view";
    private static final String LIST = "sector/list";
    private static final String UPDATE = "sector/update";
    
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
        
        List<Sector> sectors = sectorService.findAll();
        
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
        
        model.addAttribute("sector", sector);
        
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
    
        Sector sector = sectorService.findById(id);
    
        if (Objects.isNull(sector)) {
            log.info("Any sector found with ID : {}", id);
            return "redirect:/sectors/";
        }
    
        List<Site> sites = siteService.findAll();
    
        model.addAttribute("sites", sites);
        model.addAttribute("sector", sector);
    
        return UPDATE;
    }
    
    //CREATE new sector
    @GetMapping(value = "/new")
    public String createForm(Model model) {
        Sector sector = new Sector();
        List<Site> sites = siteService.findAll();
    
        model.addAttribute("sites", sites);
        model.addAttribute("sector", sector);
        return UPDATE;
    }
    
    //CREATE new sector, POST from front
    @PostMapping
    public String postSector(Sector sector) {
        sectorService.save(sector);
        return "redirect:/sectors/view/" + sector.getId();
    }
    
    //DELETE sector by ID
    @GetMapping(value = "/delete/{id}")
    public String deleteSector(@PathVariable("id") Long id) {
        log.info("DELETE sector by ID : {}", id);
        
        sectorService.delete(id);
        
        return "redirect:/sites/view";
    }
}
