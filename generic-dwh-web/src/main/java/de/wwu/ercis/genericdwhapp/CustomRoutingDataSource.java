package de.wwu.ercis.genericdwhapp;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class CustomRoutingDataSource extends AbstractRoutingDataSource {

    private static final String DEFAULT_TENANT_ID = "genericdwh_slides";

    @Override
    protected Object determineCurrentLookupKey() {

        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        // get request object
        if(attr!=null) {
            String tenantId = attr.getRequest().getParameter("db");
            System.out.println("###############tenant id"+ tenantId);
            // find parameter from request
            return tenantId;
        }else {
            return DEFAULT_TENANT_ID;
            // default data source
        }

    }
}