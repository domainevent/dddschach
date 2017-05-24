package com.iks.dddschach.rest.providers;

import com.iks.dddschach.api.SchachpartieApi.UngueltigeSpielIdException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.HashMap;
import java.util.Map;


/**
 * Behandelt die Ausnahme <code>UngueltigeSpielIdException</code> und erzeugt eine Response mit
 * Status-Code 404 und ein JSON-Objekte, das den Fehler kodiert enthält
 */
@Provider
public class UngueltigeSpielIdExceptionMapper implements ExceptionMapper<UngueltigeSpielIdException> {

    @Override
    public Response toResponse(UngueltigeSpielIdException exception) {
        Map<String, Object> json = new HashMap<>();
        json.put(ErrorCode.ERROR_CODE_KEY, ErrorCode.INVALID_GAMEID);
        json.put(ErrorCode.INVALID_GAMEID.name(), exception.spielId.toString());
        return Response.status(404).entity(json).build();
    }
}
