package com.javacook.dddschach.service.binding_rest.providers;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;


/**
 * Behandelt die Ausnahme <code>BadRequestException</code> und erzeugt eine Response mit
 * Status-Code 400 und eine Plain-Text-Nachricht, die den Fehler kodiert enthält
 */
@Provider
public class BadRequestExceptionMapper implements ExceptionMapper<BadRequestException> {

    public Response toResponse(BadRequestException exception) {
        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity(exception.getMessage())
                .type(MediaType.TEXT_PLAIN).build();
    }
}