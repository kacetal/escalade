package fr.kacetal.escalade.controllers;

import fr.kacetal.escalade.persistence.entities.Site;
import fr.kacetal.escalade.persistence.entities.Topo;
import fr.kacetal.escalade.persistence.entities.util.Comment;
import fr.kacetal.escalade.persistence.services.SiteService;
import fr.kacetal.escalade.persistence.services.util.StorageService;
import fr.kacetal.escalade.persistence.services.util.TopoService;
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
@RequestMapping(path = "/topos")
public class TopoController {
    
    private static final String TEMPLATE_DIR = "topo";
    private static final String VIEW = TEMPLATE_DIR + "/view";
    private static final String LIST = TEMPLATE_DIR + "/list";
    private static final String UPDATE = TEMPLATE_DIR + "/update";
    private static final String NEW = TEMPLATE_DIR + "/new";
    
    @Value("${default.imagename}")
    private String defaultImageName;
    
    private final TopoService topoService;
    private final SiteService siteService;
    private final StorageService storageService;
    
    @Value("${upload.path}")
    private String uploadPath;
    
    public TopoController(TopoService topoService, SiteService siteService, StorageService storageService) {
        this.topoService = topoService;
        this.siteService = siteService;
        this.storageService = storageService;
    }
    
    //READ all topos
    @GetMapping(path = {"/list", "view", ""})
    public String list(Model model) {
        
        log.info("READ all topos");
    
        Set<Topo> topos = topoService.findAll();
    
        model.addAttribute("topos", topos);
        
        log.info("Nmbr. of topos: {}", topos.size());
        
        return LIST;
    }
    
    //READ topo by ID
    @GetMapping(path = "/view/{id}")
    public String showById(@PathVariable("id") Long id, Model model) {
        log.info("READ topo by ID : {}", id);
    
        Topo topo = topoService.findById(id);
    
        if (Objects.isNull(topo)) {
            return "redirect:/topos/list";
        }
        
        log.info("Site in the topo: {}", topo.getSite());
        
        TreeSet<Comment> comments = new TreeSet<>(topo.getComments());
    
        model.addAttribute("topo", topo);
        model.addAttribute("comment", new Comment());
        model.addAttribute("comments", comments);
        
        return VIEW;
    }
    
    //UPDATE topo by ID
    @GetMapping(value = "/update/{id}")
    public String updateForm(@PathVariable("id") Long id, Model model) {
        log.info("UPDATE topo by ID : {}", id);
    
        Topo topo = topoService.findById(id);
        Set<Site> sites = siteService.findAll();
    
        model.addAttribute("sites", sites);
        model.addAttribute("topo", topo);
        
        return UPDATE;
    }
    
    //CREATE new topo
    @GetMapping(value = "/new")
    public String createForm(Model model) {
        log.info("CREATE new topo");
        
        Set<Site> sites = siteService.findAll();
    
        model.addAttribute("sites", sites);
        model.addAttribute("topo", new Topo());
        
        return NEW;
    }
    
    //CREATE new topo, POST from front
    @PostMapping
    public String postSite(@Valid Topo topo, @RequestParam("file") MultipartFile file) {
        log.info("POST new topo: {}", topo);
    
        String fileImageName = storageService.save(file);
    
        String imageName = selectImageName(topo.getImageName(), fileImageName);
    
        topo.setImageName(imageName);
    
        topo = topoService.save(topo);
        
        log.info("SAVE topo:\n{}", topo);
        
        return "redirect:/topos/view/" + topo.getId();
    }
    
    //DELETE topo by ID
    @GetMapping(value = "/delete/{id}")
    public String deleteSite(@PathVariable("id") Long id) {
        log.info("DELETE topo by ID : {}", id);
    
        topoService.delete(id);
        
        return "redirect:/topos/view";
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
