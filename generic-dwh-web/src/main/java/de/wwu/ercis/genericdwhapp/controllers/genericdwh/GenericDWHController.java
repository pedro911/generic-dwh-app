package de.wwu.ercis.genericdwhapp.controllers.genericdwh;

import de.wwu.ercis.genericdwhapp.model.genericdwh.Dimension;
import de.wwu.ercis.genericdwhapp.model.genericdwh.ReferenceObject;
import de.wwu.ercis.genericdwhapp.services.genericdwh.*;
import de.wwu.ercis.genericdwhapp.services.stats.QueryTimeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping("/genericdwh/gen_all_combinations/{db}")
    public String generateGDWHAllCombinations(@PathVariable String db, Model model){

        model.addAttribute("db", db);

        return "genericdwh/gen_all_combinations";
    }

    @GetMapping("/genericdwh/results/{db}")
    public String genericDWHQueryResults(@PathVariable String db, Model model,
                                  @RequestParam("ratioChecked") List<String> ratios,
                                  @RequestParam("dimensionChecked") List<String> dimensions,
                                  @RequestParam(name = "filters", required = false) List<String> filters) {

        model.addAttribute("db", db);
        long start = System.nanoTime();

        if (db.endsWith("dyn"))
            model.addAttribute("results", factService.gdwhDynQuery(ratios,dimensions, filters));
        else if (db.endsWith("acb"))
            model.addAttribute("results", factService.gdwhAcbQuery(ratios,dimensions, filters));
        else
            model.addAttribute("results", factService.gdwhNcbQuery(ratios,dimensions, filters));

        long end = System.nanoTime();
        Double mSec = (end - start) / 1e6;

        //generate csv with SQL to all combinations DB, take some time...
        //factService.getAllDimensionCombinations();

        model.addAttribute("timeElapsed", mSec);
        model.addAttribute("dimensions", dimensions);
        model.addAttribute("ratios", ratios);
        model.addAttribute("queryMethod", factService.queryMethod());
        model.addAttribute("query", factService.query());
        queryTimeService.addQueryTime(db,dimensions.toString()+" - "+ratios.toString(), mSec);

        return "genericdwh/results";
    }

    @RequestMapping("/genericdwh/adhoc/{db}")
    public String adhoc(@PathVariable String db, Model model){

        model.addAttribute("db", db);

        return "genericdwh/adhoc";
    }

    @GetMapping("/genericdwh/adHocResults/{db}")
    public String adhocResults(@PathVariable String db, Model model, @RequestParam("adHocQuery") String adHocQuery) {

        model.addAttribute("db", db);
        long start = System.nanoTime();

        model.addAttribute("results", factService.adHocQuery(adHocQuery));

        long end = System.nanoTime();
        Double mSec = (end - start) / 1e6;

        model.addAttribute("timeElapsed", mSec);
        model.addAttribute("query", factService.query());

        return "genericdwh/adHocResults";
    }

    @RequestMapping(method=RequestMethod.POST, value = "/genericdwh/getReferenceObjects/{db}")
    public @ResponseBody List<ReferenceObject> getSearchResultViaAjax(@RequestBody String dimensionId, Model model, @PathVariable String db ) {

        model.addAttribute("db", db);
        dimensionId = dimensionId.replace("\"","");
        Dimension dimension = dimensionService.findById(Long.parseLong(dimensionId));
        List<ReferenceObject> referenceObjects = referenceObjectService.findFirst1000ByDimensionIn(dimension);

        return referenceObjects;

    }

}
