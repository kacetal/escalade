package fr.kacetal.escalade.controllers;

import fr.kacetal.escalade.persistence.entities.Comment;
import fr.kacetal.escalade.persistence.entities.Itinerary;
import fr.kacetal.escalade.persistence.entities.Sector;
import fr.kacetal.escalade.persistence.entities.Site;
import fr.kacetal.escalade.persistence.services.ItineraryService;
import fr.kacetal.escalade.persistence.services.SectorService;
import fr.kacetal.escalade.persistence.services.SiteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.ZonedDateTime;
import java.util.Objects;

@Slf4j
@Controller
@RequestMapping(path = "/comment", method = RequestMethod.POST)
public class CommentController {
    
    private static final String SITE_VIEW = "redirect:/sites/view/";
    private static final String SECTORS_VIEW = "redirect:/sector/view/";
    private static final String ITINERARY_VIEW = "redirect:/itinerary/view/";
    
    private final SiteService siteService;
    private final SectorService sectorService;
    private final ItineraryService itineraryService;
    
    public CommentController(SiteService siteService, SectorService sectorService, ItineraryService itineraryService) {
        this.siteService = siteService;
        this.sectorService = sectorService;
        this.itineraryService = itineraryService;
    }
    
    @PostMapping("/site/{id}")
    public String commentForSite(Comment comment, @PathVariable("id") Long siteId) {
        
        Site site = siteService.findById(siteId);
        
        if (Objects.isNull(site)) {
            return SITE_VIEW;
        }
        
        comment.setCreatedOn(ZonedDateTime.now());
        
        site.getComments().add(comment);
        
        siteService.save(site);
    
        log.info("Add comment for site:\n{}\n{}", site, comment);
        
        return SITE_VIEW + siteId;
    }
    
    @PostMapping("/sector/{id}")
    public String commentForSector(Comment comment, @PathVariable("id") Long sectorId) {
    
        Sector sector = sectorService.findById(sectorId);
    
        if (Objects.isNull(sector)) {
            return SECTORS_VIEW;
        }
        
        comment.setCreatedOn(ZonedDateTime.now());
        
        sector.getComments().add(comment);
        
        sectorService.save(sector);
    
        log.info("Add comment for sector:\n{}\n{}", sector, comment);
    
    
        return SECTORS_VIEW + sectorId;
    }
    
    @PostMapping("/itinerary/{id}")
    public String commentForItinerary(Comment comment, @PathVariable("id") Long itineraryId) {
    
        Itinerary itinerary = itineraryService.findById(itineraryId);
    
        if (Objects.isNull(itinerary)) {
            return ITINERARY_VIEW;
        }
        
        comment.setCreatedOn(ZonedDateTime.now());
        
        itinerary.getComments().add(comment);
        
        itineraryService.save(itinerary);
    
        log.info("Add comment for itinerary:\n{}\n{}", itinerary, comment);
        
        return ITINERARY_VIEW + itineraryId;
    }
}
