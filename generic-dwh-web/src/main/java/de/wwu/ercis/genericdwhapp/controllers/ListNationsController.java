package de.wwu.ercis.genericdwhapp.controllers;

import de.wwu.ercis.genericdwhapp.services.standard.NationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ListNationsController {

    private final NationService nationService;

    public ListNationsController(NationService nationService) {
        this.nationService = nationService;
    }

    @RequestMapping("nations")
    public String getNationsPage(Model model){
        model.addAttribute("nations",nationService.findAll());
        return "nations";
    }
}
