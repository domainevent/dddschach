package com.javacook.dddschach.service.binding_rest.providers;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;


/**
 * Behandelt die Ausnahme <code>NotFoundException</code> und erzeugt eine Response mit
 * Status-Code 404 und eine Plain-Text-Nachricht, die den Fehler kodiert enthält
 */
@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {

    public Response toResponse(NotFoundException exception) {
        return Response
                .status(Response.Status.NOT_FOUND)
                .entity(exception.getMessage())
                .type(MediaType.TEXT_PLAIN).build();
    }
}