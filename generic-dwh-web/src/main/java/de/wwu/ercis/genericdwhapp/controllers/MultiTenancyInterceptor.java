package de.wwu.ercis.genericdwhapp.controllers;

import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class MultiTenancyInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler)
            throws Exception {
        Map<String, Object> pathVars = (Map<String, Object>) req.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        if (pathVars!=null && pathVars.containsKey("db")){
            req.setAttribute("db", pathVars.get("db"));
        }
        return true;
    }
}
