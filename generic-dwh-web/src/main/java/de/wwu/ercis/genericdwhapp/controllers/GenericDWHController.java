package de.wwu.ercis.genericdwhapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GenericDWHController {

    @RequestMapping({"/genericdwh", "/genericdwh/index", "/genericdwh/index.html","/genericdwh.html"})
    public String getIndexPage(){

        return "genericdwh/index";
    }

}
