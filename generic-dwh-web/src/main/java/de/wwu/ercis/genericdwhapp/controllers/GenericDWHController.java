package de.wwu.ercis.genericdwhapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GenericDWHController {

    @RequestMapping({"/genericdwh", "/genericdwh/index", "/genericdwh/index.html","/genericdwh.html"})
    public String getIndexPage(Model model){

        return "genericdwh/index";
    }

}
