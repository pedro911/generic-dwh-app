package de.wwu.ercis.genericdwhapp.controllers.stats;

import de.wwu.ercis.genericdwhapp.services.stats.QueryTimeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class StatsController {

    private final QueryTimeService queryTimeService;

    public StatsController(QueryTimeService queryTimeService) {
        this.queryTimeService = queryTimeService;
    }

    @RequestMapping("/{db}")
    public String getStatsPage(@PathVariable String db, Model model){

        queryTimeService.savePending();

        model.addAttribute("db", db);
        model.addAttribute("smallStarTime",queryTimeService.smallDB());
        model.addAttribute("smallLabels",queryTimeService.labels());

        //model.addAttribute("querySmallDBTimes", queryTimeService. );

        return "stats";
    }

}
