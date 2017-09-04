package com.iks.dddschach.service.binding_rest;

import org.apache.log4j.MDC;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.List;


@Provider
public class MDCFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        final String clientId = requestContext.getHeaderString("clientId");
        MDC.put("clientId", (clientId == null)? "n/a" : clientId);
        final List<PathSegment> pathSegments = requestContext.getUriInfo().getPathSegments();
        String gameId = "n/a";
        if (pathSegments.size() > 1) {
            if ("games".equals(pathSegments.get(0).toString())) {
                gameId = pathSegments.get(1).toString();
            }
        }
        MDC.put("gameId", gameId);
    }
}