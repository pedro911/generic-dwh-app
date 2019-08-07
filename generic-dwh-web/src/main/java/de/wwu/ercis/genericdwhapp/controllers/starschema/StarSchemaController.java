package de.wwu.ercis.genericdwhapp.controllers.starschema;

import de.wwu.ercis.genericdwhapp.services.starchema.CustomerService;
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

    public StarSchemaController(CustomerService customerService) {
        this.customerService = customerService;
    }


    @RequestMapping({"/starschema", "/starschema/index", "/starschema/index.html","/starschema.html"})
    public String getIndexPage(Model model){

        return "starschema/index";
    }

    @GetMapping("/starschema/results/{db}")
    public String processFindForm(@PathVariable String db, Model model,
                                  @RequestParam("ratioChecked") List<String> ratios,
                                  @RequestParam("dimensionChecked") List<String> dimensions){

        model.addAttribute("customers", customerService.findAll());
        model.addAttribute("db", db);
        long start = System.nanoTime();
        long end = System.nanoTime();
        double sec = (end - start) / 1e6;
        model.addAttribute("timeElapsed", sec);

        return "starschema/results";
    }

}
