package de.wwu.ercis.genericdwhapp.controllers.snowflakeschema;

import de.wwu.ercis.genericdwhapp.services.snowflake.springdatajpa.SnowflakeService;
import de.wwu.ercis.genericdwhapp.services.stats.QueryTimeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SnowflakeController {

    private final QueryTimeService queryTimeService;
    private final SnowflakeService snowflakeService;

    public SnowflakeController(QueryTimeService queryTimeService, SnowflakeService snowflakeService) {
        this.queryTimeService = queryTimeService;
        this.snowflakeService = snowflakeService;
    }

    @RequestMapping({"/snowflake", "/snowflake/index", "/snowflake/index.html","/snowflake.html"})
    public String getSnowFlakeIndexPage(){

        return "snowflake/index";
    }

    @RequestMapping("/snowflake/query/{db}")
    public String returnSnowflakeQueryPage(){
        return "snowflake/query";
    }

    @GetMapping("/snowflake/results/{db}")
    public String snowFlakeQueryResults(@PathVariable String db, Model model,
                                   @RequestParam("ratioChecked") List<String> ratios,
                                   @RequestParam("dimensionChecked") List<String> dimensions,
                                   @RequestParam(name = "filters", required = false) List<String> filters){

        long start = System.nanoTime();
        model.addAttribute("snowFacts", snowflakeService.snowFacts(dimensions,ratios, filters));
        long end = System.nanoTime();
        Double sec = (end - start) / 1e6;

        model.addAttribute("query", snowflakeService.query());
        model.addAttribute("timeElapsed", sec);
        model.addAttribute("db", db);
        model.addAttribute("dimensions",formatDimensions(dimensions));
        model.addAttribute("ratios", formatRatios(ratios));
        queryTimeService.addQueryTime(db,formatDimensions(dimensions).toString()+" - "+formatRatios(ratios).toString(),sec);

        return "snowflake/results";

    }

    @RequestMapping("/snowflake/adhoc/{db}")
    public String adhoc(@PathVariable String db, Model model){

        model.addAttribute("db", db);

        return "snowflake/adhoc";
    }

    @GetMapping("/snowflake/adHocResults/{db}")
    public String adhocResults(@PathVariable String db, Model model, @RequestParam("adHocQuery") String adHocQuery) {

        model.addAttribute("db", db);
        long start = System.nanoTime();

        model.addAttribute("results", snowflakeService.adHocQuery(adHocQuery));

        long end = System.nanoTime();
        Double mSec = (end - start) / 1e6;

        model.addAttribute("timeElapsed", mSec);
        model.addAttribute("query", snowflakeService.query());

        return "snowflake/adHocResults";
    }

    @RequestMapping(method= RequestMethod.POST, value = "/snowflake/getReferenceObjects/{db}")
    public @ResponseBody List<String> getSearchResultViaAjax(@RequestBody String dimensionId,
                                                             Model model, @PathVariable String db ) {

        model.addAttribute("db", db);

        dimensionId = dimensionId.replace("\"","");
        List<String> referenceObjects = snowflakeService.getReferenceObjects(dimensionId);
        return referenceObjects;

    }

    public List<String> formatRatios(List<String> ratios){
        List<String> ratiosValues = new ArrayList<>();

        for (String ratio: ratios){
            switch (ratio){
                case "selling_price": ratio = "Selling Price";
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
                case "r_name": dimension = "Region";
                    break;
                case "n_name": dimension = "Nation";
                    break;
                case "market_segment": dimension = "Market Segment";
                    break;
                case "c_name": dimension = "Customer Name";
                    break;
                case "o_clerk": dimension = "Clerk Name";
                    break;
                case "manufacturer_group": dimension = "Manufacturer Group";
                    break;
                case "product_brand": dimension = "Product Brand";
                    break;
                case "product_type": dimension = "Product Type";
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
