package de.wwu.ercis.genericdwhapp.controllers.starschema;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class StarSchemaController {

    @RequestMapping({"/starschema", "/starschema/index", "/starschema/index.html","/starschema.html"})
    public String getIndexPage(Model model){

        return "starschema/index";
    }

}
