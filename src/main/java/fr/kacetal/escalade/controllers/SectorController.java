package fr.kacetal.escalade.controllers;

import fr.kacetal.escalade.persistence.entities.Sector;
import fr.kacetal.escalade.persistence.services.SectorService;
import fr.kacetal.escalade.persistence.services.SiteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/sectors")
public class SectorController {
    
    private final SectorService sectorService;
    
    private final SiteService siteService;
    
    public SectorController(SectorService sectorService, SiteService siteService) {
        this.sectorService = sectorService;
        this.siteService = siteService;
    }
    
    @GetMapping(path = "/view")
    public String list(Model model) {
    
        List<Sector> sectors = sectorService.findAll();
    
        model.addAttribute("sectors", sectors);
    
        return "sectors";
    }
}
