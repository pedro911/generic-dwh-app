package de.wwu.ercis.genericdwhapp.controllers.starschema;

import de.wwu.ercis.genericdwhapp.services.starchema.CustomerService;
import de.wwu.ercis.genericdwhapp.services.starchema.StarSchemaFactService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@Controller
public class StarSchemaController {

    private final CustomerService customerService;
    private final StarSchemaFactService starSchemaFactService;


    public StarSchemaController(CustomerService customerService, StarSchemaFactService starSchemaFactService) {
        this.customerService = customerService;
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
        model.addAttribute("starSchemaFacts", starSchemaFactService.facts(dimensions, ratios));
        long end = System.nanoTime();
        double sec = (end - start) / 1e6;
        model.addAttribute("timeElapsed", sec);
        model.addAttribute("db", db);

        return "starschema/results";
    }

}
