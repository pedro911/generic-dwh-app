package de.wwu.ercis.genericdwhapp.controllers.genericdwh;

import com.google.gson.Gson;
import de.wwu.ercis.genericdwhapp.model.genericdwh.Dimension;
import de.wwu.ercis.genericdwhapp.model.genericdwh.ReferenceObject;
import de.wwu.ercis.genericdwhapp.services.genericdwh.*;
import de.wwu.ercis.genericdwhapp.services.stats.QueryTimeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
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
                                  @RequestParam("dimensionChecked") List<String> dimensions) throws FileNotFoundException {

        model.addAttribute("db", db);
        long start = System.nanoTime();

        if (db.endsWith("dyn"))
            model.addAttribute("results", factService.gdwhDynQuery(ratios,dimensions));
        else if (db.endsWith("acb"))
            model.addAttribute("results", factService.gdwhAcbQuery(ratios,dimensions));
        else
            model.addAttribute("results", factService.gdwhNcbQuery(ratios,dimensions));

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

    @PostMapping("/genericdwh/getReferenceObjects")
    public ResponseEntity<?> getSearchResultViaAjax(@RequestBody String dimension_id, Errors errors) {

        //If error, just return a 400 bad request, along with the error message
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body("Error!");
        }

        Dimension dimension = dimensionService.findById(Long.parseLong(dimension_id));
        List<ReferenceObject> referenceObjects = referenceObjectService.findAllByDimensionIn(dimension);
        String json = new Gson().toJson(referenceObjects);

        return ResponseEntity.ok(json);

    }

}
