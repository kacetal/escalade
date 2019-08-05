package fr.kacetal.escalade.controllers.util;

import fr.kacetal.escalade.persistence.entities.Itinerary;
import fr.kacetal.escalade.persistence.entities.Sector;
import fr.kacetal.escalade.persistence.entities.Site;
import fr.kacetal.escalade.persistence.entities.Topo;
import fr.kacetal.escalade.persistence.entities.util.Grade;
import fr.kacetal.escalade.persistence.services.ItineraryService;
import fr.kacetal.escalade.persistence.services.SectorService;
import fr.kacetal.escalade.persistence.services.SiteService;
import fr.kacetal.escalade.persistence.services.util.TopoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/search")
public class SearchController {
    
    private static final String TEMPLATE_DIR = "search";
    private static final String SITES = TEMPLATE_DIR + "/sites";
    private static final String SECTORS = TEMPLATE_DIR + "/sectors";
    private static final String ITINERARIES = TEMPLATE_DIR + "/itineraries";
    private static final String TOPOS = TEMPLATE_DIR + "/topos";
    
    private final SiteService siteService;
    private final SectorService sectorService;
    private final ItineraryService itineraryService;
    private final TopoService topoService;
    
    public SearchController(SiteService siteService,
                            SectorService sectorService,
                            ItineraryService itineraryService, TopoService topoService) {
        this.siteService = siteService;
        this.sectorService = sectorService;
        this.itineraryService = itineraryService;
        this.topoService = topoService;
    }
    
    @GetMapping("/sites")
    public String sitesSearch(
            Model model,
            @RequestParam(value = "name", defaultValue = "", required = false) String name,
            @RequestParam(value = "country", defaultValue = "", required = false) String country,
            @RequestParam(value = "region", defaultValue = "", required = false) String region
    ) {
        Set<Site> sitesByName = siteService.findByName(name);
        Set<Site> sitesByCountry = siteService.findByCountry(country);
        Set<Site> sitesByRegion = siteService.findByRegion(region);
        
        Set<Site> sites = getIntersectedCollection(sitesByName, sitesByCountry, sitesByRegion);
        //form attributes
        model.addAttribute("name", name);
        model.addAttribute("country", country);
        model.addAttribute("region", region);
        //result
        model.addAttribute("sites", sites);
        
        return SITES;
    }
    
    
    @GetMapping("/sectors")
    public String sectorsSearch(
            Model model,
            @RequestParam(value = "name", defaultValue = "", required = false) String name,
            @RequestParam(value = "siteName", defaultValue = "", required = false) String siteName
    ) {
        Set<Sector> sectorsByName = sectorService.findByName(name);
        Set<Sector> sectorsBySiteName = sectorService.findBySiteName(siteName);
        
        Set<Sector> sectors = getIntersectedCollection(sectorsByName, sectorsBySiteName);
        
        Set<Site> sites = siteService.findAll();
        //form attributes
        model.addAttribute("name", name);
        model.addAttribute("siteName", siteName);
        model.addAttribute("sectors", sectors);
        //result
        model.addAttribute("sites", sites);
        
        return SECTORS;
    }
    
    @GetMapping("/itineraries")
    public String itinerariesSearch(
            Model model,
            @RequestParam(value = "name", defaultValue = "", required = false) String name,
            @RequestParam(value = "spit", defaultValue = "", required = false) String spit,
            @RequestParam(value = "strHeight", defaultValue = "", required = false) String strHeight,
            @RequestParam(value = "strNumberOfParts", defaultValue = "", required = false) String strNumberOfParts,
            @RequestParam(value = "strGrade", defaultValue = "", required = false) String strGrade,
            @RequestParam(value = "sectorName", defaultValue = "", required = false) String sectorName
    ) {
        Integer height = parseInteger(strHeight);
        Integer numberOfParts = parseInteger(strNumberOfParts);
        Grade grade = parseGrade(strGrade);
        
        Set<Itinerary> itinerariesByName = itineraryService.findByName(name);
        Set<Itinerary> itinerariesBySpit = itineraryService.findBySpit(spit);
        Set<Itinerary> itinerariesByHeight = itineraryService.findByHeight(height);
        Set<Itinerary> itinerariesByNumberOfParts = itineraryService.findByNumberOfParts(numberOfParts);
        Set<Itinerary> itinerariesByGrade = itineraryService.findByGrade(grade);
        Set<Itinerary> itinerariesBySector = null;
        
        Optional<Set<Sector>> OptSectorsByName = Optional.ofNullable(sectorService.findByName(sectorName));
        if (OptSectorsByName.isPresent()) {
            itinerariesBySector = OptSectorsByName.get()
                    .stream()
                    .map(Sector::getId)
                    .map(itineraryService::findBySectorId)
                    .flatMap(Collection::stream)
                    .collect(Collectors.toSet());
        }
    
        Set<Itinerary> itineraries = getIntersectedCollection(
                itinerariesByName,
                itinerariesBySpit,
                itinerariesByHeight,
                itinerariesByNumberOfParts,
                itinerariesByGrade,
                itinerariesBySector);
        
        Set<Sector> sectors = sectorService.findAll();
        Set<Site> sites = siteService.findAll();
        
        //form attributes
        model.addAttribute("name", name);
        model.addAttribute("spit", spit);
        model.addAttribute("strHeight", strHeight);
        model.addAttribute("strNumberOfParts", strNumberOfParts);
        model.addAttribute("strGrade", strGrade);
        model.addAttribute("grades", Grade.values());
        model.addAttribute("sectorName", sectorName);
        
        model.addAttribute("sectors", sectors);
        model.addAttribute("sites", sites);
        //result
        model.addAttribute("itineraries", itineraries);
        
        return ITINERARIES;
    }
    
    @GetMapping("/topos")
    public String toposSearch(
            Model model,
            @RequestParam(value = "name", defaultValue = "", required = false) String name,
            @RequestParam(value = "siteName", defaultValue = "", required = false) String siteName
    ) {
        Set<Topo> toposByName = topoService.findByName(name);
        Set<Topo> toposBySiteName = topoService.findBySiteName(siteName);
    
        Set<Topo> topos = getIntersectedCollection(toposByName, toposBySiteName);
    
        Set<Site> sites = siteService.findAll();
        //form attributes
        model.addAttribute("name", name);
        model.addAttribute("siteName", siteName);
        model.addAttribute("topos", topos);
        model.addAttribute("sites", sites);
        
        return TOPOS;
    }
    
    private Integer parseInteger(final String strInteger) {
        if (strInteger.matches("[+-]?\\d+")) {
            try {
                return Integer.valueOf(strInteger);
            } catch (NumberFormatException e) {
                log.warn("The string '{}' for parsing is not a correct number", strInteger);
                log.warn("NumberFormatException:", e);
            }
        } else {
            log.warn("The string '{}' for parsing is not a correct number", strInteger);
        }
        return -1;
    }
    
    private Grade parseGrade(final String strGrade) {
        if ("-1".equals(strGrade)) {
            return Grade.EMPTY;
        }
        try {
            return Grade.valueOf(strGrade);
        } catch (IllegalArgumentException e) {
            log.warn("The string '{}' for parsing is not a correct Grade name", strGrade);
            log.warn("IllegalArgumentException:", e);
            return Grade.EMPTY;
        }
    }
    
    @SafeVarargs
    private <T> Set<T> getIntersectedCollection(final Set<T>... collections) {
        return Arrays.stream(collections)
                .filter(Objects::nonNull)
                .reduce((x, y) -> {
                    x.retainAll(y);
                    return x;
                }).orElse(Collections.emptySet());
    }
}
