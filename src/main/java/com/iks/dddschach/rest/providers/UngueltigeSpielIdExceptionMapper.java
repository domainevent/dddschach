package com.iks.dddschach.rest.providers;


import com.iks.dddschach.api.SchachpartieApi;
import com.iks.dddschach.api.SchachpartieApi.UngueltigeSpielIdException;
import com.iks.dddschach.api.SchachpartieApi.UngueltigerHalbzugException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by vollmer on 19.05.17.
 */
@Provider
public class UngueltigeSpielIdExceptionMapper implements ExceptionMapper<UngueltigeSpielIdException> {

    @Override
    public Response toResponse(UngueltigeSpielIdException exception) {
        Map<String, Object> json = new HashMap<>();
        json.put(ErrorCode.ERROR_CODE_KEY, ErrorCode.INVALID_GAMEID);
        json.put(ErrorCode.INVALID_GAMEID.name(), exception.spielId.toString());
        return Response.status(422).entity(json).build();
    }
}
