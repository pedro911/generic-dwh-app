package de.wwu.ercis.genericdwhapp.bootstrap;

import de.wwu.ercis.genericdwhapp.model.genericdwh.*;
import de.wwu.ercis.genericdwhapp.services.genericdwh.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DataLoader implements CommandLineRunner {

    private final DimensionService dimensionService;
    private final DimensionCombinationService dimensionCombinationService;
    private final DimensionHierarchyService dimensionHierarchyService;
    private final FactService factService;
    private final RatioCombinationService ratioCombinationService;
    private final RatioService ratioService;
    private final ReferenceObjectCombinationService referenceObjectCombinationService;
    private final ReferenceObjectHierarchyService referenceObjectHierarchyService;
    private final ReferenceObjectService referenceObjectService;

    @Autowired
    public DataLoader(DimensionService dimensionService, DimensionCombinationService dimensionCombinationService,
                      DimensionHierarchyService dimensionHierarchyService, FactService factService,
                      RatioCombinationService ratioCombinationService, RatioService ratioService,
                      ReferenceObjectCombinationService referenceObjectCombinationService,
                      ReferenceObjectHierarchyService referenceObjectHierarchyService,
                      ReferenceObjectService referenceObjectService) {
        this.dimensionService = dimensionService;
        this.dimensionCombinationService = dimensionCombinationService;
        this.dimensionHierarchyService = dimensionHierarchyService;
        this.factService = factService;
        this.ratioCombinationService = ratioCombinationService;
        this.ratioService = ratioService;
        this.referenceObjectCombinationService = referenceObjectCombinationService;
        this.referenceObjectHierarchyService = referenceObjectHierarchyService;
        this.referenceObjectService = referenceObjectService;
    }

    @Override
    public void run(String... args) throws Exception {

        //only for tests using H2 In Memory
        //loadSlideData();

    }

    private void loadSlideData() {

        //Dimensions
        Dimension month = new Dimension("Month", true);
        dimensionService.save(month);

        Dimension productCategory = new Dimension("Product Category", false);
        dimensionService.save(productCategory);

        Dimension monthProductCategory = new Dimension("Month - Product Category", true);
        dimensionService.save(monthProductCategory);

        Dimension country = new Dimension("Country", false);
        dimensionService.save(country);

        Dimension state = new Dimension("State", false);
        dimensionService.save(state);

        //Dimension Combinations
        DimensionCombination dc_monthProductCategory1 = new DimensionCombination(monthProductCategory.getId(), month.getId());
        dimensionCombinationService.save(dc_monthProductCategory1);

        DimensionCombination dc_monthProductCategory2 = new DimensionCombination(monthProductCategory.getId(), productCategory.getId());
        dimensionCombinationService.save(dc_monthProductCategory2);

        //Dimension Hierarchies
        DimensionHierarchy dh_country_state = new DimensionHierarchy(country.getId(), state.getId());
        dimensionHierarchyService.save(dh_country_state);

        //Reference Objects
        ReferenceObject ro1 = new ReferenceObject("January 2017", month, "month#2017_01", month.is_time());
        referenceObjectService.save(ro1);

        ReferenceObject ro2 = new ReferenceObject("February 2017", month, "month#2017_02",month.is_time());
        referenceObjectService.save(ro2);

        ReferenceObject ro3 = new ReferenceObject("March 2017", month, "month#2017_03", month.is_time());
        referenceObjectService.save(ro3);

        ReferenceObject ro4 = new ReferenceObject("Dairy Products", productCategory, "product#dairy",
                productCategory.is_time());
        referenceObjectService.save(ro4);

        ReferenceObject ro5 = new ReferenceObject("Beverages",productCategory, "product#beverages",
                productCategory.is_time());
        referenceObjectService.save(ro5);

        ReferenceObject ro6 = new ReferenceObject("Jan 2017 dairy products", monthProductCategory,
                "month-product-category#jan2017-dairyproducts", monthProductCategory.is_time());
        referenceObjectService.save(ro6);

        ReferenceObject ro7 = new ReferenceObject("Germany", country, "country#Germnay", country.is_time());
        referenceObjectService.save(ro7);

        ReferenceObject ro8 = new ReferenceObject("NRW", state, "state#NRW", state.is_time());
        referenceObjectService.save(ro8);

        //Reference Objects Combinations
        ReferenceObjectCombination roc1 = new ReferenceObjectCombination(ro6.getId(),ro1.getId());
        referenceObjectCombinationService.save(roc1);

        ReferenceObjectCombination roc2 = new ReferenceObjectCombination(ro6.getId(),ro4.getId());
        referenceObjectCombinationService.save(roc2);

        //Reference Object Hierarchies
        ReferenceObjectHierarchy referenceObjectHierarchy1 = new ReferenceObjectHierarchy(ro7.getId(),ro8.getId());
        referenceObjectHierarchyService.save(referenceObjectHierarchy1);

        //Ratios
        Ratio ratio1 = new Ratio("Turnover");
        ratioService.save(ratio1);

        Ratio ratio2 = new Ratio("Profit");
        ratioService.save(ratio2);

        Ratio ratio3 = new Ratio("Costs");
        ratioService.save(ratio3);

        Ratio ratio4 = new Ratio("Shelf space rentability");
        ratioService.save(ratio4);

        Ratio ratio5 = new Ratio("Direct product rentability");
        ratioService.save(ratio5);

        Ratio ratio6 = new Ratio("Days of inventory");
        ratioService.save(ratio6);

        //Ratio Combinations
        RatioCombination ratioCombination1 = new RatioCombination(ratio2.getId(),ratio1.getId(),"+");
        ratioCombinationService.save(ratioCombination1);

        RatioCombination ratioCombination2 = new RatioCombination(ratio2.getId(),ratio3.getId(),"-");
        ratioCombinationService.save(ratioCombination2);

        //Facts
        Fact f1 = new Fact(ratio1.getId(),ro1.getId(),288317.28);
        factService.save(f1);

        Fact f2 = new Fact(ratio1.getId(),ro2.getId(),275234.09);
        factService.save(f2);

        Fact f3 = new Fact(ratio1.getId(),ro3.getId(),234582.02);
        factService.save(f3);

        Fact f6 = new Fact(ratio1.getId(),ro6.getId(),32784.53);
        factService.save(f6);

        Fact f7 = new Fact(ratio1.getId(),ro7.getId(),3924230.23);
        factService.save(f7);

        Fact f8 = new Fact(ratio1.getId(),ro8.getId(),302138.87);
        factService.save(f8);
    }
}
