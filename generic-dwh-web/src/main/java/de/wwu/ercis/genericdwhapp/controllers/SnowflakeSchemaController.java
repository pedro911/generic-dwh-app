package de.wwu.ercis.genericdwhapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SnowflakeSchemaController {

    @RequestMapping({"/snowflakeschema", "/snowflakeschema/index", "/snowflakeschema/index.html","/snowflakeschema.html"})
    public String getIndexPage(Model model){

        return "snowflakeschema/index";
    }

}
