package de.wwu.ercis.genericdwhapp.controllers.stats;

import de.wwu.ercis.genericdwhapp.services.stats.QueryStringService;
import de.wwu.ercis.genericdwhapp.services.stats.QueryTimeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class StatsController {

    private final QueryTimeService queryTimeService;
    private final QueryStringService queryStringService;

    public StatsController(QueryTimeService queryTimeService, QueryStringService queryStringService) {
        this.queryTimeService = queryTimeService;
        this.queryStringService = queryStringService;
    }

    @RequestMapping("/{db}")
    public String getStatsPage(@PathVariable String db, Model model){

        queryTimeService.savePending();

        model.addAttribute("db", db);
        List<String[]> smallQueryIds = queryTimeService.labels("small");
        List<String[]> OneGBQueryIds = queryTimeService.labels("1gb");

        model.addAttribute("smallLabels",smallQueryIds);
        model.addAttribute("queryStringsSmall",queryStringService.findAllByQueryIds(smallQueryIds));
        model.addAttribute("smallGdwhACBQueryTime",queryTimeService.top5slowestQueries("small","small_acb"));
        model.addAttribute("smallGdwhNCBQueryTime",queryTimeService.top5slowestQueries("small","small_ncb"));
        model.addAttribute("smallStarQueryTime",queryTimeService.top5slowestQueries("small","star_small"));
        model.addAttribute("smallSnowQueryTime",queryTimeService.top5slowestQueries("small","snow_small"));

        model.addAttribute("oneGBLabels",OneGBQueryIds);
        model.addAttribute("queryStrings1GB",queryStringService.findAllByQueryIds(OneGBQueryIds));
        model.addAttribute("oneGBGdwhACBQueryTime",queryTimeService.top5slowestQueries("1gb","1gb_acb"));
        model.addAttribute("oneGBGdwhNCBQueryTime",queryTimeService.top5slowestQueries("1gb","1gb_ncb"));
        model.addAttribute("oneGBStarQueryTime",queryTimeService.top5slowestQueries("1gb","star_1gb"));
        model.addAttribute("oneGBSnowQueryTime",queryTimeService.top5slowestQueries("1gb","snow_1gb"));

        model.addAttribute("gdwhACBCountQueries",queryTimeService.countQueries("acb"));
        model.addAttribute("gdwhNCBCountQueries",queryTimeService.countQueries("ncb"));
        model.addAttribute("starCountQueries",queryTimeService.countQueries("star"));
        model.addAttribute("snowCountQueries",queryTimeService.countQueries("snow"));

        return "stats";
    }

}
