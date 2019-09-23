package de.wwu.ercis.genericdwhapp.controllers.genericdwh;

import de.wwu.ercis.genericdwhapp.services.genericdwh.*;
import de.wwu.ercis.genericdwhapp.services.stats.QueryTimeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@Controller
public class GenericDWHController {

    private final DimensionService dimensionService;
    private final DimensionCombinationService dimensionCombinationService;
    private final DimensionHierarchyService dimensionHierarchyService;
    private final ReferenceObjectService referenceObjectService;
    private final ReferenceObjectCombinationService referenceObjectCombinationService;
    private final ReferenceObjectHierarchyService referenceObjectHierarchyService;
    private final RatioService ratioService;
    private final RatioCombinationService ratioCombinationService;
    private final FactService factService;
    private final QueryTimeService queryTimeService;

    public GenericDWHController(DimensionService dimensionService, DimensionCombinationService dimensionCombinationService,
                                DimensionHierarchyService dimensionHierarchyService, ReferenceObjectService referenceObjectService,
                                ReferenceObjectCombinationService referenceObjectCombinationService,
                                ReferenceObjectHierarchyService referenceObjectHierarchyService, RatioService ratioService,
                                RatioCombinationService ratioCombinationService, FactService factService,
                                QueryTimeService queryTimeService) {
        this.dimensionService = dimensionService;
        this.dimensionCombinationService = dimensionCombinationService;
        this.dimensionHierarchyService = dimensionHierarchyService;
        this.referenceObjectService = referenceObjectService;
        this.referenceObjectCombinationService = referenceObjectCombinationService;
        this.referenceObjectHierarchyService = referenceObjectHierarchyService;
        this.ratioService = ratioService;
        this.ratioCombinationService = ratioCombinationService;
        this.factService = factService;
        this.queryTimeService = queryTimeService;
    }

    @RequestMapping({"/genericdwh", "/genericdwh/index", "/genericdwh/index.html", "/genericdwh.html"})
    public String getGenericDWHIndexPage() {
        return "genericdwh/index";
    }


    @RequestMapping("/genericdwh/list_data/{db}")
    public String listAllData(@PathVariable String db, Model model) {
        model.addAttribute("db", db);
        model.addAttribute("dimensions", dimensionService.findByOrderByIdAsc());
        model.addAttribute("dimensionsCombinations", dimensionCombinationService.findByOrderByCombinationIdAsc());
        model.addAttribute("dimensionsHierarchies", dimensionHierarchyService.findByOrderByParentIdAsc());
        model.addAttribute("referenceObjects", referenceObjectService.findByOrderByIdAsc());
        model.addAttribute("referenceObjectsCombinations", referenceObjectCombinationService.findByOrderByCombinationIdAsc());
        model.addAttribute("referenceObjectsHierarchies", referenceObjectHierarchyService.findByOrderByParentIdAsc());
        model.addAttribute("ratios", ratioService.findByOrderByIdAsc());
        model.addAttribute("ratiosCombinations", ratioCombinationService.findByOrderByCombinationIdAsc());
        model.addAttribute("facts", factService.findAll());
        return "genericdwh/list_data";
    }

    @RequestMapping("/genericdwh/query/{db}")
    public String returnGenericDWHQueryPage(@PathVariable String db, Model model){

        model.addAttribute("db", db);
        model.addAttribute("dimensionsCombinations", dimensionCombinationService.findDimensionsByCombinationId());
        model.addAttribute("dimensionRoots",dimensionHierarchyService.findAllByRoot());
        model.addAttribute("ratioRoots", ratioService.findAllByRoot());

        return "genericdwh/query";
    }

    @GetMapping("/genericdwh/results/{db}")
    public String genericDWHQueryResults(@PathVariable String db, Model model,
                                  @RequestParam("ratioChecked") List<String> ratios,
                                  @RequestParam("dimensionChecked") List<String> dimensions) {

        model.addAttribute("db", db);
        long start = System.nanoTime();
        if (db.endsWith("dyn"))
            model.addAttribute("results", factService.gdwhDynQuery(ratios,dimensions));
        else if (db.endsWith("acb"))
            model.addAttribute("results", factService.gdwhAcbQuery(ratios,dimensions));
        else
            model.addAttribute("results", factService.gdwhStdQuery(ratios,dimensions));
        long end = System.nanoTime();
        Double sec = (end - start) / 1e6;

        model.addAttribute("timeElapsed", sec);
        model.addAttribute("dimensions", dimensions);
        model.addAttribute("ratios", ratios);
        model.addAttribute("queryMethod", factService.queryMethod());
        model.addAttribute("query", factService.query());
        queryTimeService.addQueryTime(db,dimensions.toString()+" - "+ratios.toString(), sec);

        return "genericdwh/results";
    }



}
