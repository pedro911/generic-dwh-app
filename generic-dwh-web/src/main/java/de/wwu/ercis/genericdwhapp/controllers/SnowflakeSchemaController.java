package de.wwu.ercis.genericdwhapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SnowflakeSchemaController {

    @RequestMapping({"/snowflakeschema", "/snowflakeschema/index", "/snowflakeschema/index.html","/snowflakeschema.html"})
    public String getIndexPage(){

        return "snowflakeschema/index";
    }

    @RequestMapping("/starschema/query/{db}")
    public String returnSmallQuery(){
        return "starschema/query";
    }


}
