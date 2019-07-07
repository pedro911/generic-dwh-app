package de.wwu.ercis.genericdwhapp.controllers.genericdwh;

import de.wwu.ercis.genericdwhapp.services.genericdwh.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class QueryController {

    private final DimensionService dimensionService;
    private final DimensionCombinationService dimensionCombinationService;
    private final DimensionHierarchyService dimensionHierarchyService;
    private final ReferenceObjectService referenceObjectService;
    private final ReferenceObjectCombinationService referenceObjectCombinationService;
    private final ReferenceObjectHierarchyService referenceObjectHierarchyService;
    private final RatioService ratioService;
    private final RatioCombinationService ratioCombinationService;
    private final FactService factService;

    public QueryController(DimensionService dimensionService, DimensionCombinationService dimensionCombinationService, DimensionHierarchyService dimensionHierarchyService, ReferenceObjectService referenceObjectService, ReferenceObjectCombinationService referenceObjectCombinationService, ReferenceObjectHierarchyService referenceObjectHierarchyService, RatioService ratioService, RatioCombinationService ratioCombinationService, FactService factService) {
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

    @RequestMapping("/genericdwh/query/{db}")
    public String returnQuery(@PathVariable String db, Model model){
        model.addAttribute("db", db);
        model.addAttribute("dimensions",dimensionService.findByOrderByIdAsc());
        //model.addAttribute("dimensions",dimensionService.findByRoot());
        model.addAttribute("dimensionsCombinations", dimensionCombinationService.findByOrderByCombinationIdAsc());
        model.addAttribute("dimensionsHierarchies",dimensionHierarchyService.findByOrderByParentIdAsc());
        //model.addAttribute("dimensionsHierarchies",dimensionService.findByRoot());
        model.addAttribute("dimensionRoots",dimensionHierarchyService.findAllByRoot());
        model.addAttribute("referenceObjects", referenceObjectService.findByOrderByIdAsc());
        model.addAttribute("referenceObjectsCombinations", referenceObjectCombinationService.findByOrderByCombinationIdAsc());
        model.addAttribute("referenceObjectsHierarchies", referenceObjectHierarchyService.findByOrderByParentIdAsc());
        model.addAttribute("ratios", ratioService.findByOrderByIdAsc());
        model.addAttribute("ratiosCombinations", ratioCombinationService.findByOrderByCombinationIdAsc());
        model.addAttribute("facts", factService.findByOrderByRatioIdAsc());
        return "genericdwh/query";
    }
}
