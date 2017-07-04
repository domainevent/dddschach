package com.iks.dddschach.rest.providers;

import com.iks.dddschach.rest.RestService;
import com.sun.org.apache.regexp.internal.RE;
import org.apache.log4j.Logger;

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

    final static Logger LOG = Logger.getLogger(GeneralExceptionMapper.class);

    @Override
    public Response toResponse(Exception e) {
        try {
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
        catch (Throwable t) {
            // Verhindert, dass es zu endlosen Rekursionen kommt.
            t.printStackTrace();
            LOG.fatal("Exception occured in GeneralExceptionMapper", t);
            return Response.serverError().build();
        }
    }
}
