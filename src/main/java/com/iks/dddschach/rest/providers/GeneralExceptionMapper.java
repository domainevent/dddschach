package com.iks.dddschach.rest.providers;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;


/**
 * Created by javacook on 29.04.17.
 */
@Provider
public class GeneralExceptionMapper extends Exception implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception e) {
        String messages = e.toString();
        Throwable cause = e.getCause();

        while (cause != null) {
            messages += " <= " + cause.getMessage();
            cause = cause.getCause();
        }
        return Response
                .status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(messages)
                .type(MediaType.TEXT_PLAIN).build();
    }
}
