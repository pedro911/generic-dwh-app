package de.wwu.ercis.genericdwhapp.controllers;

import de.wwu.ercis.genericdwhapp.services.DimensionService;
import de.wwu.ercis.genericdwhapp.services.ReferenceObjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    private final DimensionService dimensionService;
    private final ReferenceObjectService referenceObjectService;

    public IndexController(DimensionService dimensionService, ReferenceObjectService referenceObjectService) {
        this.dimensionService = dimensionService;
        this.referenceObjectService = referenceObjectService;
    }

    @RequestMapping({"", "/", "index", "index.html"})
    public String getIndexPage(Model model){

        model.addAttribute("dimensions",dimensionService.findAll());
        model.addAttribute("referenceObjects", referenceObjectService.findAll());

        return "index";
    }
}
