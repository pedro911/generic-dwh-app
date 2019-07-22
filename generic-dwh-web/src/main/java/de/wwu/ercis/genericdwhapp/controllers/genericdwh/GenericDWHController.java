package de.wwu.ercis.genericdwhapp.controllers.genericdwh;

import de.wwu.ercis.genericdwhapp.model.genericdwh.*;
import de.wwu.ercis.genericdwhapp.services.genericdwh.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
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

    public GenericDWHController(DimensionService dimensionService, DimensionCombinationService dimensionCombinationService, DimensionHierarchyService dimensionHierarchyService, ReferenceObjectService referenceObjectService, ReferenceObjectCombinationService referenceObjectCombinationService, ReferenceObjectHierarchyService referenceObjectHierarchyService, RatioService ratioService, RatioCombinationService ratioCombinationService, FactService factService) {
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

    @RequestMapping({"/genericdwh", "/genericdwh/index", "/genericdwh/index.html", "/genericdwh.html"})
    public String getIndexPage() {
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
        model.addAttribute("facts", factService.findByOrderByRatioIdAsc());
        return "genericdwh/list_data";
    }

    @GetMapping("/genericdwh/results/{db}")
    public String processFindForm(@PathVariable String db, Model model,
                                  @RequestParam("ratioChecked") List<String> ratios,
                                  @RequestParam("dimensionChecked") List<String> dimensions,
                                  @RequestParam("dCombinations") List<String> dCombinations) {

/*        log.debug("List of checked ratios");
        ratios.forEach(System.out::println);

        log.debug("List of checked dimensions - hierarchies");
        dimensions.forEach(System.out::println);

        log.debug("List of dimension Combinations the root WHERE IN() clause");
        dCombinations.forEach(System.out::println);*/

        model.addAttribute("db", db);
        Dimension dimension = new Dimension();
        Ratio ratio = new Ratio();
        Double value_sum = Double.valueOf(0);
        List<Fact> resultFinal = new ArrayList<Fact>();
        String modus = "";
        long start = System.nanoTime();
        // this uses referenceObjectCombination
        // check error if selecting more than one ratio
        for (String dimension_root : dCombinations) {
            for (String radio_id : ratios) {
                ratio = ratioService.findById(Long.parseLong(radio_id));
                for (String dimension_id : dimensions) {
                    dimension = dimensionService.findById(Long.parseLong(dimension_id));
                    List<ReferenceObject> referenceObjectsResults = referenceObjectService.findAllByDimensionIn(dimension);
                    if (null == factService.findFirstByReferenceObjectIdAndRatioId(referenceObjectsResults.get(0).getId(),Long.parseLong(radio_id))) {
                        for (ReferenceObject referenceObjectResult : referenceObjectsResults) {
                            Fact resultFact = new Fact();
                            resultFact.setReferenceObject(referenceObjectResult);
                            resultFact.setRatio(ratio);
                            List<ReferenceObjectCombination> referenceObjectsFactValues = referenceObjectCombinationService.findAllBySubordinateId(referenceObjectResult.getId());
                            for (ReferenceObjectCombination ro2 : referenceObjectsFactValues) {
                                Fact tempFact = factService.findByReferenceObjectIdAndRatioId(ro2.getCombinationId(), ratio.getId());
                                value_sum = value_sum + tempFact.getValue();
                            }
                            resultFact.setReferenceObjectId(referenceObjectResult.getId());
                            resultFact.setRatioId(Long.parseLong(radio_id));
                            resultFact.setValue(value_sum);
                            resultFinal.add(resultFact);
                            factService.save(resultFact);
                            value_sum = Double.valueOf(0);
                        }
                        if (modus.startsWith("R") && !modus.contains("and") ) modus = modus + " and New Facts inserted";
                        else modus = "New Facts inserted";
                    }
                    else {
                        for (ReferenceObject referenceObjectResult : referenceObjectsResults) {
                            Fact resultFact = factService.findByReferenceObjectIdAndRatioId(referenceObjectResult.getId(), Long.parseLong(radio_id));
                            resultFinal.add(resultFact);
                        }
                        if (modus.startsWith("N") && !modus.contains("and") ) modus = modus + " and Returned Existing Facts";
                        else modus = "Returned Existing Facts";
                    }
                }
            }
        }
        long end = System.nanoTime();
        double sec = (end - start) / 1e6;

        model.addAttribute("results", resultFinal);
        model.addAttribute("timeElapsed", sec);
        model.addAttribute("modus", modus);

        return "genericdwh/results";
    }
}
