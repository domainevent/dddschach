package com.iks.dddschach.rest.providers;

import com.iks.dddschach.service.api.SchachpartieApi.UngueltigerHalbzugException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.HashMap;
import java.util.Map;


/**
 * Behandelt die Ausnahme <code>UngueltigerHalbzugException</code> und erzeugt eine Response mit
 * Status-Code 422 und ein JSON-Objekte, das den Fehler kodiert enthält
 */
@Provider
public class UngueltigerHalbzugExceptionMapper implements ExceptionMapper<UngueltigerHalbzugException> {

    @Override
    public Response toResponse(UngueltigerHalbzugException exception) {
        Map<String, Object> json = new HashMap<>();
        json.put(ErrorCode.ERROR_CODE_KEY, ErrorCode.INVALID_MOVE);
        if (exception.halbzug != null) {
            json.put(ErrorCode.INVALID_MOVE.name(), exception.halbzug.toString());
            if (exception.verletzteZugregel != null) {
                json.put(exception.halbzug.toString(), exception.verletzteZugregel.name());
            }
        }
        return Response.status(422).entity(json).build();
    }
}
