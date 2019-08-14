package de.wwu.ercis.genericdwhapp.controllers.tpchstandard;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TPCHStdController {

    @RequestMapping({"/tpchstandard", "/tpchstandard/index", "/tpchstandard/index.html","/tpchstandard.html"})
    public String getIndexPage(Model model){

        return "tpchstandard/index";
    }

}
