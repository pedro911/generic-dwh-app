package de.wwu.ercis.genericdwhapp.controllers;

import de.wwu.ercis.genericdwhapp.services.standard.onegb.NationOnegbService;
import de.wwu.ercis.genericdwhapp.services.standard.small.NationSmallService;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Profile("alldb")
@RequestMapping("/tpch/nations")
public class ListNationsController {

    private final NationSmallService nationSmallService;
    private final NationOnegbService nationOnegbService;

    public ListNationsController(NationSmallService nationSmallService, NationOnegbService nationOnegbService) {
        this.nationSmallService = nationSmallService;
        this.nationOnegbService = nationOnegbService;
    }

    @RequestMapping("/small")
    public String getNationsPageSmall(Model model) {
        model.addAttribute("nations", nationSmallService.findAll(Sort.by(Sort.Direction.ASC, "nNationkey")));
        return "nations";
    }

    @RequestMapping("/onegb")
    public String getNationsPageOneGB(Model model) {
        model.addAttribute("nations", nationOnegbService.findAll(Sort.by(Sort.Direction.ASC, "nNationkey")));
        return "nations";
    }

}
