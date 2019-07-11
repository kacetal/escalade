package fr.kacetal.escalade.controllers.util;

import fr.kacetal.escalade.persistence.entities.Itinerary;
import fr.kacetal.escalade.persistence.entities.Sector;
import fr.kacetal.escalade.persistence.entities.Site;
import fr.kacetal.escalade.persistence.entities.util.Grade;
import fr.kacetal.escalade.persistence.services.ItineraryService;
import fr.kacetal.escalade.persistence.services.SectorService;
import fr.kacetal.escalade.persistence.services.SiteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

@Slf4j
@Controller
@RequestMapping("/search")
public class SearchController {
    
    private final SiteService siteService;
    private final SectorService sectorService;
    private final ItineraryService itineraryService;
    
    public SearchController(SiteService siteService,
                            SectorService sectorService,
                            ItineraryService itineraryService) {
        this.siteService = siteService;
        this.sectorService = sectorService;
        this.itineraryService = itineraryService;
    }
    
    @GetMapping("/sites/")
    public String searchSite(
            Model model,
            @RequestParam(value = "name", defaultValue = "", required = false) String name,
            @RequestParam(value = "country", defaultValue = "", required = false) String country,
            @RequestParam(value = "region", defaultValue = "", required = false) String region
    ) {
        Set<Site> sites = new HashSet<>();
        
        siteService.findByName(name).forEach(sites::add);
        siteService.findByCountry(country).forEach(sites::add);
        siteService.findByRegion(region).forEach(sites::add);
        
        model.addAttribute("sites", sites);
        
        return null;
    }
    
    @GetMapping("/sectors/")
    public String searchSector(
            Model model,
            @RequestParam(value = "name", defaultValue = "", required = false) String name,
            @RequestParam(value = "siteId", required = false) String strSiteId
    ) {
        Set<Sector> sectors = new TreeSet<>();
        
        Long siteId = parseLong(strSiteId);
        
        sectorService.findByName(name).forEach(sectors::add);
        sectorService.findBySiteId(siteId).forEach(sectors::add);
        
        Set<Site> sites = siteService.findAll();
        
        model.addAttribute("sectors", sectors);
        model.addAttribute("sites", sites);
        
        return null;
    }
    
    @GetMapping("/itineraries/")
    public String searchItinerary(
            Model model,
            @RequestParam(value = "name", defaultValue = "", required = false) String name,
            @RequestParam(value = "spit", defaultValue = "", required = false) String spit,
            @RequestParam(value = "height", required = false) String strHeight,
            @RequestParam(value = "numberOfParts", required = false) String strNumberOfParts,
            @RequestParam(value = "grade", required = false) String grade,
            @RequestParam(value = "sectorId", required = false) String strSectorId,
            @RequestParam(value = "siteId", required = false) String strSiteId) {
        
        TreeSet<Itinerary> itineraries = new TreeSet<>();
        
        Integer height = parseInteger(strHeight);
        Integer numberOfParts = parseInteger(strNumberOfParts);
        Long sectorId = parseLong(strSectorId);
        Long siteId = parseLong(strSiteId);
        
        itineraryService.findByName(name).forEach(itineraries::add);
        itineraryService.findBySpit(spit).forEach(itineraries::add);
        itineraryService.findByHeight(height).forEach(itineraries::add);
        itineraryService.findByNumberOfParts(numberOfParts).forEach(itineraries::add);
        itineraryService.findByGrade(Grade.valueOf(grade)).forEach(itineraries::add);
        itineraryService.findBySectorId(sectorId).forEach(itineraries::add);
        itineraryService.findBySiteId(siteId).forEach(itineraries::add);
        
        Set<Sector> sectors = sectorService.findAll();
        Set<Site> sites = siteService.findAll();
        
        model.addAttribute("itineraries", itineraries);
        model.addAttribute("sectors", sectors);
        model.addAttribute("sites", sites);
        
        return null;
    }
    
    private Long parseLong(String strLong) {
        if (Objects.nonNull(strLong)) {
            try {
                return Long.valueOf(strLong);
            } catch (NumberFormatException e) {
                log.error("Number for parsing is not a number or null", e);
            }
        }
        return null;
    }
    
    private Integer parseInteger(String strInteger) {
        if (Objects.nonNull(strInteger)) {
            try {
                return Integer.valueOf(strInteger);
            } catch (NumberFormatException e) {
                log.error("Number for parsing is not a number or null", e);
            }
        }
        return null;
    }
}
