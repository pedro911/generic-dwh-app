package de.wwu.ercis.genericdwhapp.controllers.genericdwh;

import de.wwu.ercis.genericdwhapp.model.genericdwh.Dimension;
import de.wwu.ercis.genericdwhapp.model.genericdwh.Fact;
import de.wwu.ercis.genericdwhapp.model.genericdwh.Ratio;
import de.wwu.ercis.genericdwhapp.model.genericdwh.ReferenceObject;
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

    @RequestMapping({"/genericdwh", "/genericdwh/index", "/genericdwh/index.html","/genericdwh.html"})
    public String getIndexPage(){
        return "genericdwh/index";
    }


    @RequestMapping("/genericdwh/list_data/{db}")
    public String listAllData(@PathVariable String db, Model model){
        model.addAttribute("db", db);
        model.addAttribute("dimensions",dimensionService.findByOrderByIdAsc());
        model.addAttribute("dimensionsCombinations", dimensionCombinationService.findByOrderByCombinationIdAsc());
        model.addAttribute("dimensionsHierarchies",dimensionHierarchyService.findByOrderByParentIdAsc());
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
                                  @RequestParam("dCombinations") List<String> dCombinations){

        //many checks, if dimension is null, if ratios null, etc...
        // if all selected, do joins etc...

        log.debug("List of checked ratios");
        ratios.forEach(System.out::println);

        log.debug("List of checked dimensions - hierarchies");
        dimensions.forEach(System.out::println);

        log.debug("List of dimension Combinations the root WHERE IN() clause");
        dCombinations.forEach(System.out::println);

        model.addAttribute("db", db);
        Dimension dimension = new Dimension();

        Ratio ratio = new Ratio();
        Double value_sum = Double.valueOf(0);
        List<Fact> resultFinal = new ArrayList<Fact>();

        for (String dimension_root:dCombinations){
            Dimension dimensionRoot = dimensionService.findById(Long.parseLong(dimension_root));
            for(String radio_id:ratios){
                ratio = ratioService.findById(Long.parseLong(radio_id));
                for(String dimension_id:dimensions){
                    dimension = dimensionService.findById(Long.parseLong(dimension_id));

                    List<ReferenceObject> referenceObjectsRoots = referenceObjectService.findAllByDimensionIn(dimension);
                    log.debug("List of reference objects from dimension");
                    for (ReferenceObject referenceObjectsRoot : referenceObjectsRoots) {

                        List<ReferenceObject> referenceObjectsFactValues = new ArrayList<>();

                        Fact resultFact = new Fact();
                        resultFact.setRatioId(ratio.getId());
                        resultFact.setReferenceObjectId(referenceObjectsRoot.getId());
                        resultFact.setReferenceObject(referenceObjectsRoot);
                        resultFact.setRatio(ratio);

                        referenceObjectsFactValues = referenceObjectService.findAllByDimensionInAndNameContaining(dimensionRoot,referenceObjectsRoot.getName());

                        for (ReferenceObject ro2 : referenceObjectsFactValues) {
                            Fact tempFact = factService.findByReferenceObjectAndRatio(ro2, ratio);
                            value_sum = value_sum + tempFact.getValue();
                        }
                        log.debug("Value TOTAL");
                        resultFact.setValue(value_sum);
                        resultFinal.add(resultFact);
                        System.out.println(resultFact.getValue());
                        value_sum = Double.valueOf(0);
                    }
                    log.debug("Results contains:");
                    resultFinal.forEach( fact -> System.out.println(fact.getReferenceObject().getName()));
                }
            }
        }

        model.addAttribute("results", resultFinal);
        return "genericdwh/results";
    }

}
