package de.wwu.ercis.genericdwhapp.bootstrap;

import de.wwu.ercis.genericdwhapp.model.Dimension;
import de.wwu.ercis.genericdwhapp.model.DimensionCombination;
import de.wwu.ercis.genericdwhapp.model.DimensionHierarchy;
import de.wwu.ercis.genericdwhapp.services.DimensionCombinationService;
import de.wwu.ercis.genericdwhapp.services.DimensionHierarchyService;
import de.wwu.ercis.genericdwhapp.services.DimensionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final DimensionService dimensionService;
    private final DimensionCombinationService dimensionCombinationService;
    private final DimensionHierarchyService dimensionHierarchyService;

    @Autowired
    public DataLoader(DimensionService dimensionService, DimensionCombinationService dimensionCombinationService, DimensionHierarchyService dimensionHierarchyService) {
        this.dimensionService = dimensionService;
        this.dimensionCombinationService = dimensionCombinationService;
        this.dimensionHierarchyService = dimensionHierarchyService;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("################");
        System.out.println("Load data.......");
        System.out.println("################");

        loadData();

        System.out.println("################");
        System.out.println("............End!");
        System.out.println("################");
    }

    private void loadData() {

        System.out.println("Start loading initial data from slides");

        Dimension month = new Dimension();
        month.setName("Month");
        dimensionService.save(month);

        Dimension productCategory = new Dimension();
        productCategory.setName("Product Category");
        dimensionService.save(productCategory);

        Dimension monthProductCategory = new Dimension();
        monthProductCategory.setName("Month - Product Category");
        dimensionService.save(monthProductCategory);

        Dimension country = new Dimension();
        country.setName("Country");
        dimensionService.save(country);

        Dimension state = new Dimension();
        state.setName("State");
        dimensionService.save(state);

        DimensionCombination dc_monthProductCategory1 = new DimensionCombination();
        dc_monthProductCategory1.setComponent(monthProductCategory);
        dc_monthProductCategory1.setAggregate(month);
        dimensionCombinationService.save(dc_monthProductCategory1);

        DimensionCombination dc_monthProductCategory2 = new DimensionCombination();
        dc_monthProductCategory2.setComponent(monthProductCategory);
        dc_monthProductCategory2.setAggregate(productCategory);
        dimensionCombinationService.save(dc_monthProductCategory2);

        DimensionHierarchy dh_country_state = new DimensionHierarchy();
        dh_country_state.setParent(country);
        dh_country_state.setChild(state);
        dimensionHierarchyService.save(dh_country_state);

    }
}
