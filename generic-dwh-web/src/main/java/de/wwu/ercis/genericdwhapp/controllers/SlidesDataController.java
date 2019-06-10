package de.wwu.ercis.genericdwhapp.controllers;

import de.wwu.ercis.genericdwhapp.services.genericdwh.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SlidesDataController {

    private final DimensionService dimensionService;
    private final DimensionCombinationService dimensionCombinationService;
    private final DimensionHierarchyService dimensionHierarchyService;
    private final ReferenceObjectService referenceObjectService;
    private final ReferenceObjectCombinationService referenceObjectCombinationService;
    private final ReferenceObjectHierarchyService referenceObjectHierarchyService;
    private final RatioService ratioService;
    private final RatioCombinationService ratioCombinationService;
    private final FactService factService;

    public SlidesDataController(DimensionService dimensionService,
                                DimensionCombinationService dimensionCombinationService,
                                DimensionHierarchyService dimensionHierarchyService,
                                ReferenceObjectService referenceObjectService,
                                ReferenceObjectCombinationService referenceObjectCombinationService,
                                ReferenceObjectHierarchyService referenceObjectHierarchyService,
                                RatioService ratioService, RatioCombinationService ratioCombinationService,
                                FactService factService) {
        this.dimensionService = dimensionService;
        this.dimensionCombinationService = dimensionCombinationService;
        this.dimensionHierarchyService = dimensionHierarchyService;
        this.referenceObjectService = referenceObjectService;
        this.referenceObjectCombinationService = referenceObjectCombinationService;
        this.referenceObjectHierarchyService = referenceObjectHierarchyService;
        this.ratioService = ratioService;
        this.ratioCombinationService = ratioCombinationService;
        this.factService = factService;
    }

    @RequestMapping("/genericdwh/slidesdata")
    public String getIndexPage(Model model){
        // only on dimension implemented: it can be sorted by Sort.by + property:
        //model.addAttribute("dimensions",dimensionService.findAll(Sort.by(Sort.Direction.ASC, "name")));
        //or sort by ID without using Sort
        model.addAttribute("dimensions",dimensionService.findByOrderByIdAsc());
        model.addAttribute("dimensionsCombinations", dimensionCombinationService.findByOrderByIdAsc());
        model.addAttribute("dimensionsHierarchies",dimensionHierarchyService.findByOrderByIdAsc());
        model.addAttribute("referenceObjects", referenceObjectService.findByOrderByIdAsc());
        model.addAttribute("referenceObjectsCombinations", referenceObjectCombinationService.findByOrderByIdAsc());
        model.addAttribute("referenceObjectsHierarchies", referenceObjectHierarchyService.findByOrderByIdAsc());
        model.addAttribute("ratios", ratioService.findByOrderByIdAsc());
        model.addAttribute("ratiosCombinations", ratioCombinationService.findByOrderByIdAsc());
        model.addAttribute("facts", factService.findByOrderByIdAsc());
        return "genericdwh/slidesdata";
    }

}
