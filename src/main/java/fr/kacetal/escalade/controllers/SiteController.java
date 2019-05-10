package fr.kacetal.escalade.controllers;

import fr.kacetal.escalade.persistence.entities.Site;
import fr.kacetal.escalade.persistence.services.SiteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Objects;

@Slf4j
@Controller
@RequestMapping(path = "/sites")
public class SiteController {
    
    private SiteService siteService;
    
    public SiteController() {
    }
    
    public SiteController(SiteService siteService) {
        this.siteService = siteService;
    }
    
    // READ all sites
    @GetMapping()
    public String list(Model model) {
        
        log.info("READ all sites");
        
        List<Site> sites = siteService.findAll();
        
        model.addAttribute("sites", sites);
        
        log.info("No. of sites: {}", sites.size());
        
        return "sites";
    }
    
    //READ list of sites by Name
    @GetMapping(params = "name")
    public String showByName(@PathParam("name") String name, Model model) {
        log.info("READ list of sites by name : \"{}\"", name);
        
        List<Site> sites = siteService.findSitesByName(name);
        
        model.addAttribute("sites", sites);
        
        log.info("No. of sites: {}", sites.size());
        
        return "sites";
    }
    
    //READ one site by ID
    @GetMapping(params = "id")
    public String showByID(@PathParam("id") Long id, Model model) {
        log.info("READ one site by ID : {}", id);
        
        Site site = siteService.findById(id);
        
        if (Objects.isNull(site)) {
            return "redirect:/sites/";
        }
        
        model.addAttribute("site", site);
        
        return "show";
    }
    
    //UPDATE site by ID
    @GetMapping(value = "/edit/{id}")
    public String updateForm(@PathVariable Long id, Model model) {
        model.addAttribute("site", siteService.findById(id));
        return "update";
    }
    
    //CREATE new site
    @GetMapping(value = "/new")
    public String createForm(Model model) {
        Site site = new Site();
        model.addAttribute("site", site);
        return "update";
    }
    
    //CREATE new site, POST from front
    @PostMapping
    public String saveSite(@Valid Site site) {
        siteService.save(site);
        return "redirect:/sites/?id=" + site.getId();
    }
    
    //DELETE site by ID
    @GetMapping(value = "/delete/{id}")
    public String deleteSite(@PathVariable("id") Long id) {
        log.info("DELETE site by ID : {}", id);
        
        siteService.delete(id);
        
        return "redirect:/sites/";
    }
}
