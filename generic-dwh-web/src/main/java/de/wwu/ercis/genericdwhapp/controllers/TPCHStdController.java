package de.wwu.ercis.genericdwhapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TPCHStdController {

    @RequestMapping({"/tpch_std", "/tpch_std/index", "/tpch_std/index.html","/tpch_std.html"})
    public String getIndexPage(Model model){

        return "tpch_std/index";
    }

}
