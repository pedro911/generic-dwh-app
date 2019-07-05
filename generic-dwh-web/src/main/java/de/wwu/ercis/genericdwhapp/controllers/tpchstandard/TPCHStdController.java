package de.wwu.ercis.genericdwhapp.controllers.tpchstandard;

import de.wwu.ercis.genericdwhapp.services.tpchstandard.NationService;
import de.wwu.ercis.genericdwhapp.services.tpchstandard.RegionService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TPCHStdController {

    private final NationService nationService;
    private final RegionService regionService;

    public TPCHStdController(NationService nationService, RegionService regionService) {
        this.nationService = nationService;
        this.regionService = regionService;
    }

    @RequestMapping({"/tpchstandard", "/tpchstandard/index", "/tpchstandard/index.html","/tpchstandard.html"})
    public String getIndexPage(Model model){

        return "tpchstandard/index";
    }

    @RequestMapping("tpchstandard/query/{db}")
    public String getNations(@PathVariable String db, Model model) {
        model.addAttribute("db", db);
        model.addAttribute("nations", nationService.findAll(Sort.by(Sort.Direction.ASC, "nNationkey")));
        model.addAttribute("regions", regionService.findAll(Sort.by(Sort.Direction.ASC, "rRegionkey")));
        return "tpchstandard/query";
    }


}
