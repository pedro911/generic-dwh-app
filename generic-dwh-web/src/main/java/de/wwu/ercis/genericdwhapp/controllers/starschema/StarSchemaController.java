package de.wwu.ercis.genericdwhapp.controllers.starschema;

import de.wwu.ercis.genericdwhapp.services.starchema.StarSchemaFactService;
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
public class StarSchemaController {

    private final StarSchemaFactService starSchemaFactService;

    public StarSchemaController(StarSchemaFactService starSchemaFactService) {
        this.starSchemaFactService = starSchemaFactService;
    }

    @RequestMapping({"/starschema", "/starschema/index", "/starschema/index.html","/starschema.html"})
    public String getIndexPage(Model model){
        return "starschema/index";
    }

    @GetMapping("/starschema/results/{db}")
    public String processFindForm(@PathVariable String db, Model model,
                                  @RequestParam("ratioChecked") List<String> ratios,
                                  @RequestParam("dimensionChecked") List<String> dimensions){

        long start = System.nanoTime();
        model.addAttribute("starSchemaFacts", starSchemaFactService.starFacts(dimensions,ratios));
        long end = System.nanoTime();
        double sec = (end - start) / 1e6;

        model.addAttribute("query", starSchemaFactService.query());
        model.addAttribute("timeElapsed", sec);
        model.addAttribute("db", db);
        model.addAttribute("dimensions",formatDimensions(dimensions));
        model.addAttribute("ratios", formatRatios(ratios));

        return "starschema/results";
    }

    public List<String> formatRatios(List<String> ratios){
        List<String> ratiosValues = new ArrayList<>();

        for (String ratio: ratios){
            switch (ratio){
                case "l_extendedprice": ratio = "Product Price";
                    break;
                case "l_quantity": ratio = "Purchase Amount";
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
                case "o_orderpriority": dimension = "Order Priority";
                    break;
                case "o_orderstatus": dimension = "Order Status";
                    break;
                case "o_orderkey": dimension = "Order Key";
                    break;
                case "o_clerk": dimension = "Order Clerk";
                    break;
                case "p_type": dimension = "Product Type";
                    break;
                case "p_brand": dimension = "Product Brand";
                    break;
                case "p_name": dimension = "Product Name";
                    break;
                case "d_year_number": dimension = "Year";
                    break;
                case "d_month_number": dimension = "Month";
                    break;
                default: dimension = "Dimension Not Found";
                    break;
            }
            dimensionsValues.add(dimension);
        }
        return dimensionsValues;
    }

}
