package fr.kacetal.escalade.controllers;

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

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@Slf4j
@Controller
@RequestMapping(path = "/sites")
public class SiteController {
    
    private static final String VIEW = "site/view";
    private static final String LIST = "site/list";
    private static final String UPDATE = "site/update";
    
    private final SiteService siteService;
    
    private final SectorService sectorService;
    
    public SiteController(SiteService siteService, SectorService sectorService) {
        this.siteService = siteService;
        this.sectorService = sectorService;
    }
    
    
    // READ all sites
    @GetMapping(path = {"/view", ""})
    public String list(Model model) {
        
        log.info("READ all sites");
        
        List<Site> sites = siteService.findAll();
        
        model.addAttribute("sites", sites);
        
        log.info("No. of sites: {}", sites.size());
        
        return LIST;
    }
    
    //SEARCH site by ID
    @GetMapping(path = "/view/{id}")
    public String showById(@PathVariable("id") Long id, Model model) {
        log.info("READ one site by ID : {}", id);
        
        Site site = siteService.findById(id);
        
        if (Objects.isNull(site)) {
            return "redirect:/sites/";
        }
        
        List<Sector> sectors = sectorService.findBySite(site);
        
        log.info("No. of sectors: {}", sectors.size());
        
        model.addAttribute("site", site);
        model.addAttribute("sectors", sectors);
        
        return VIEW;
    }
    /*
    //SEARCH sites by name
    @GetMapping(path = "/search/{name}")
    public String showByName(@PathVariable("name") String name, Model model) {
        log.info("SEARCH sites by name : \"{}\"", name);
        
        List<Site> sites = siteService.findByName(name);
        
        model.addAttribute("sites", sites);
        
        log.info("No. of sites searched: {}", sites.size());
        
        return LIST;
    }*/
    
    //UPDATE site by ID
    @GetMapping(value = "/update/{id}")
    public String updateForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("site", siteService.findById(id));
        return UPDATE;
    }
    
    //CREATE new site
    @GetMapping(value = "/new")
    public String createForm(Model model) {
        Site site = new Site();
        model.addAttribute("site", site);
        return UPDATE;
    }
    
    //CREATE new site, POST from front
    @PostMapping
    public String postSite(@Valid Site site) {
        siteService.save(site);
        return "redirect:/sites/view/" + site.getId();
    }
    
    //DELETE site by ID
    @GetMapping(value = "/delete/{id}")
    public String deleteSite(@PathVariable("id") Long id) {
        log.info("DELETE site by ID : {}", id);
        
        siteService.delete(id);
        
        return "redirect:/sites/view";
    }
}
