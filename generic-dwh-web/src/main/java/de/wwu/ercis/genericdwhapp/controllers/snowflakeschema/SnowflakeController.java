package de.wwu.ercis.genericdwhapp.controllers.snowflakeschema;

import de.wwu.ercis.genericdwhapp.services.snowflake.springdatajpa.SnowflakeService;
import de.wwu.ercis.genericdwhapp.services.stats.QueryTimeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String starQueryResults(@PathVariable String db, Model model,
                                   @RequestParam("ratioChecked") List<String> ratios,
                                   @RequestParam("dimensionChecked") List<String> dimensions){

        long start = System.nanoTime();
        model.addAttribute("snowFacts", snowflakeService.snowFacts(dimensions,ratios));
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

    public List<String> formatRatios(List<String> ratios){
        List<String> ratiosValues = new ArrayList<>();

        for (String ratio: ratios){
            switch (ratio){
                case "lineitem_price": ratio = "Sell Price";
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
                case "r_name": dimension = "Region";
                    break;
                case "n_name": dimension = "Nation";
                    break;
                case "m_mktsegment": dimension = "Market Segment";
                    break;
                case "c_name": dimension = "Customer Name";
                    break;
                case "o_orderpriority": dimension = "Order Priority";
                    break;
                case "o_shippriority": dimension = "Order Ship Priority";
                    break;
                case "o_orderstatus": dimension = "Order Status";
                    break;
                case "o_orderkey": dimension = "Order Number";
                    break;
                case "o_clerk": dimension = "Order Clerk";
                    break;
                case "l_returnflag": dimension = "Item Return Flag";
                    break;
                case "l_linestatus": dimension = "Item Status";
                    break;
                case "l_shipinstruct": dimension = "Item Ship Instruct";
                    break;
                case "l_shipmode": dimension = "Item Ship Mode";
                    break;
                case "p_mfgr": dimension = "Manufacturer Group";
                    break;
                case "p_brand": dimension = "Brand";
                    break;
                case "p_type": dimension = "Product Type";
                    break;
                case "p_size": dimension = "Product Size";
                    break;
                case "p_container": dimension = "Container";
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
