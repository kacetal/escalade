package fr.kacetal.escalade.controllers;

import fr.kacetal.escalade.persistence.entities.Comment;
import fr.kacetal.escalade.persistence.entities.Site;
import fr.kacetal.escalade.persistence.services.SiteService;
import fr.kacetal.escalade.persistence.services.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

@Slf4j
@Controller
@RequestMapping(path = "/sites")
public class SiteController {
    
    private static final String VIEW = "site/view";
    private static final String LIST = "site/list";
    private static final String UPDATE = "site/update";
    private static final String NEW = "site/new";
    
    private final SiteService siteService;
    private final StorageService storageService;
    
    @Value("${upload.path}")
    private String uploadPath;
    
    public SiteController(SiteService siteService, StorageService storageService) {
        this.siteService = siteService;
        this.storageService = storageService;
    }
    
    // READ all sites
    @GetMapping(path = {"/view", ""})
    public String list(Model model) {
        
        log.info("READ all sites");
        
        Set<Site> sites = siteService.findAll();
        
        model.addAttribute("sites", sites);
        
        log.info("No. of sites: {}", sites.size());
        
        return LIST;
    }
    
    //SEARCH site by ID
    @GetMapping(path = "/view/{id}")
    public String showById(@PathVariable("id") Long id, Model model) {
        log.info("READ site by ID : {}", id);
        
        Site site = siteService.findById(id);
        
        if (Objects.isNull(site)) {
            return "redirect:/sites/";
        }
        
        log.info("No. of sectors: {}", site.getSectors().size());
        
        TreeSet<Comment> comments = new TreeSet<>(site.getComments());
        
        model.addAttribute("site", site);
        model.addAttribute("comment", new Comment());
        model.addAttribute("comments", comments);
        
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
        log.info("UPDATE site by ID : {}", id);
        
        Site site = siteService.findById(id);
        
        model.addAttribute("site", site);
        
        return UPDATE;
    }
    
    //CREATE new site
    @GetMapping(value = "/new")
    public String createForm(Model model) {
        log.info("CREATE new site");
        
        model.addAttribute("site", new Site());
        
        return NEW;
    }
    
    //CREATE new site, POST from front
    @PostMapping
    public String postSite(@Valid Site site, @RequestParam("file") MultipartFile file) {
        
        String imageName = storageService.save(file);
        
        site.setImageName(imageName);
        
        Site savedSite = siteService.save(site);
        
        log.info("SAVE site:\n{}", savedSite);
        
        return "redirect:/sites/view/" + savedSite.getId();
    }
    
    //DELETE site by ID
    @GetMapping(value = "/delete/{id}")
    public String deleteSite(@PathVariable("id") Long id) {
        log.info("DELETE site by ID : {}", id);
        
        siteService.delete(id);
        
        return "redirect:/sites/view";
    }
}
