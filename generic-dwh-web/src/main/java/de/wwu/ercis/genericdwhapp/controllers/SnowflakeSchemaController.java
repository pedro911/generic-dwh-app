package de.wwu.ercis.genericdwhapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SnowflakeSchemaController {

    @RequestMapping({"/snowflakeschema", "/snowflakeschema/index", "/snowflakeschema/index.html","/snowflakeschema.html"})
    public String getIndexPage(){

        return "snowflakeschema/index";
    }

    @RequestMapping("/starschema/small/query")
    public String returnSmallQuery(){
        return "starschema/small/query";
    }

    @RequestMapping("/starschema/1gb/query")
    public String return1gbQuery(){
        return "starschema/1gb/query";
    }


}
