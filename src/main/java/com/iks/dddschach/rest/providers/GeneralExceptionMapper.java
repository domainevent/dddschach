package com.iks.dddschach.rest.providers;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;


/**
 * Behandelt alle Ausnahme, die nicht durch die anderen ExceptionMapper behandelt werden und erzeugt
 * eine Response mit Status-Code 500 und eine Plain-Text-Nachricht, die den Fehler kodiert enthält
 */
@SuppressWarnings("serial")
@Provider
public class GeneralExceptionMapper extends Exception implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception e) {
        String messages = e.toString();
        Throwable cause = e.getCause();

        while (cause != null) {
            messages += " <= " + cause;
            cause = cause.getCause();
        }
        return Response
                .status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(messages)
                .type(MediaType.TEXT_PLAIN).build();
    }
}
