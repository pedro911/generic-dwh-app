package de.wwu.ercis.genericdwhapp.controllers.starschema;

import de.wwu.ercis.genericdwhapp.services.star.StarService;
import de.wwu.ercis.genericdwhapp.services.stats.QueryTimeService;
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
public class StarController {

    private final QueryTimeService queryTimeService;
    private final StarService starService;

    public StarController(QueryTimeService queryTimeService, StarService starService) {
        this.queryTimeService = queryTimeService;
        this.starService = starService;
    }

    @RequestMapping({"/star", "/star/index", "/star/index.html","/star.html"})
    public String getStarIndexPage(Model model){
        return "star/index";
    }

    @RequestMapping("/star/query/{db}")
    public String returnStarQueryPage(){
        return "star/query";
    }

    @GetMapping("/star/results/{db}")
    public String starQueryResults(@PathVariable String db, Model model,
                                  @RequestParam("ratioChecked") List<String> ratios,
                                  @RequestParam("dimensionChecked") List<String> dimensions){

        long start = System.nanoTime();
        model.addAttribute("starFacts", starService.starFacts(dimensions,ratios));
        long end = System.nanoTime();
        Double sec = (end - start) / 1e6;

        model.addAttribute("query", starService.query());
        model.addAttribute("timeElapsed", sec);
        model.addAttribute("db", db);
        model.addAttribute("dimensions",formatDimensions(dimensions));
        model.addAttribute("ratios", formatRatios(ratios));
        queryTimeService.addQueryTime(db,formatDimensions(dimensions).toString()+" - "+formatRatios(ratios).toString(),sec);

        return "star/results";
    }


    public List<String> formatRatios(List<String> ratios){
        List<String> ratiosValues = new ArrayList<>();

        for (String ratio: ratios){
            switch (ratio){
                case "lineitem_price": ratio = "Sell Price";
                    break;
                case "l_quantity": ratio = "Purchase Amount";
                    break;
                case "product_cost": ratio = "Product Cost";
                    break;
            }
            ratiosValues.add(ratio);
        }
        return ratiosValues;
    }

    public List<String> formatDimensions(List<String> dimensions){
        List<String> dimensionsValues = new ArrayList<>();

        for (String dimension: dimensions){
            switch (dimension){
                case "c_r_name": dimension = "Region";
                    break;
                case "c_n_name": dimension = "Nation";
                    break;
                case "c_mktsegment": dimension = "Market Segment";
                    break;
                case "c_name": dimension = "Customer Name";
                    break;
                case "o_clerk": dimension = "Order Clerk";
                    break;
                case "p_mfgr": dimension = "Manufacturer Group";
                    break;
                case "p_brand": dimension = "Product Brand";
                    break;
                case "p_type": dimension = "Product Type";
                    break;
                case "p_name": dimension = "Product Name";
                    break;
                case "d_year_number": dimension = "Year";
                    break;
                case "d_month_number": dimension = "Month";
                    break;
                case "s_name": dimension = "Supplier Name";
                    break;
                default: dimension = "Dimension Not Found";
                    break;
            }
            dimensionsValues.add(dimension);
        }
        return dimensionsValues;
    }

}
