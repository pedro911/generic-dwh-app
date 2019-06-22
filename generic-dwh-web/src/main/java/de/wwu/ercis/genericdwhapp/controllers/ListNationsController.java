package de.wwu.ercis.genericdwhapp.controllers;

import de.wwu.ercis.genericdwhapp.services.standard.NationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tpch/nations")
public class ListNationsController {

    @Autowired
    private final NationService nationService;
    public ListNationsController(NationService nationService) {
        this.nationService = nationService;
    }

    @RequestMapping("/{db}")
    public String getNationsPageOneGB(@PathVariable String db, Model model) {
        model.addAttribute("db", db);
        model.addAttribute("nations", nationService.findAll(Sort.by(Sort.Direction.ASC, "nNationkey")));
        return "tpch_std/nations";
    }

}
