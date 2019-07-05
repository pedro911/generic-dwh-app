package de.wwu.ercis.genericdwhapp.controllers.stats;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class StatsController {

    @RequestMapping("stats")
    public String getStatsPage(){

        return "stats";
    }

}
