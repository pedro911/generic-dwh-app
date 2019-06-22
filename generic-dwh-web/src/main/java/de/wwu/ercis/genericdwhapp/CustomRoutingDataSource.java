package de.wwu.ercis.genericdwhapp;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class CustomRoutingDataSource extends AbstractRoutingDataSource {

    private static final String DEFAULT_TENANT_ID = "genericdwh_slides";

    @Override
    protected Object determineCurrentLookupKey() {

        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        // get request object
        if(attr!=null) {
            String db = (String) RequestContextHolder.getRequestAttributes().getAttribute("db", RequestAttributes.SCOPE_REQUEST);
            // find parameter from request
            return db;
        }else {
            return DEFAULT_TENANT_ID;
            // default data source
        }

    }
}