package com.iks.dddschach.rest.providers;


import com.iks.dddschach.api.SchachpartieApi;
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
public class UngueltigerHalbzugExceptionMapper implements ExceptionMapper<UngueltigerHalbzugException> {

    @Override
    public Response toResponse(UngueltigerHalbzugException exception) {
        Map<String, Object> json = new HashMap<>();
        json.put(ErrorCode.ERROR_CODE_KEY, ErrorCode.INVALID_MOVE);
        json.put(ErrorCode.INVALID_MOVE.name(), exception.halbzug.toString());
        return Response.status(422).entity(json).build();
    }
}
