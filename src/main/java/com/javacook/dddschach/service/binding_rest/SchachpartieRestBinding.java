package com.javacook.dddschach.service.binding_rest;

import com.javacook.dddschach.domain.*;
import com.javacook.dddschach.service.api.*;
import com.webcohesion.enunciate.metadata.rs.ResponseCode;
import com.webcohesion.enunciate.metadata.rs.StatusCodes;
import org.apache.log4j.Logger;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.util.Date;


/**
 * Dieser REST-Service stellt die Möglichkeit bereit, eine Schach-Partie "remote" über das Netzwerk
 * zu spielen.
 */
@Path("/")
public class SchachpartieRestBinding implements SchachpartieApi {

    @Context
    SchachpartieApi schachpartieApi;

    @Context
    UriInfo uriInfo;

    final static Logger LOG = Logger.getLogger(SchachpartieRestBinding.class);


    /**
     * Ueberprueft, ob die Anwendung aktiv ist
     *
     * @return Eine Bestätigungsmeldung mit aktuellem Datum und Uhrzeit
     */
    @GET
    @Path("isalive")
    @Produces(MediaType.TEXT_PLAIN)
    public String isAlive()
    {
        LOG.info("DDD-Schach lebt");
        return "DDD-Schach is alive: " + new Date();
    }


    @Override
    @POST
    @Path("neuesSpiel")
    @StatusCodes({
            @ResponseCode(code = 200, condition = "ok"),
            @ResponseCode(code = 500, condition = "An exception occured")
    })
    public NeuesSpielResponse neuesSpiel(@Valid @NotNull NeuesSpielRequest request) {
        final NeuesSpielResponse response = schachpartieApi.neuesSpiel(request);
        LOG.info("Neue Partie mit Spiel-ID='" + response.getSpielId() + ", Vermerk='" + request.getVermerk() + "'");
        return response;
    }


    @Override
    @POST
    @Path("fuehreHalbzugAus")
    @StatusCodes({
            @ResponseCode(code = 200, condition = "ok"),
            @ResponseCode(code = 400, condition = "Input validation error"),
            @ResponseCode(code = 500, condition = "An exception occured")})
    public FuehreHalbzugAusResponse fuehreHalbzugAus(@Valid @NotNull FuehreHalbzugAusRequest request)
            throws UngueltigerHalbzugException, UngueltigeSpielIdException {
            return schachpartieApi.fuehreHalbzugAus(request);
    }


    @Override
    @POST
    @Path("aktuellesSpielbrett")
    @StatusCodes({
            @ResponseCode(code = 200, condition = "ok"),
            @ResponseCode(code = 400, condition = "Input validation error"),
            @ResponseCode(code = 500, condition = "An exception occured")})
    public AktuellesSpielbrettResponse aktuellesSpielbrett(@Valid @NotNull AktuellesSpielbrettRequest request)
            throws UngueltigeSpielIdException {
        return schachpartieApi.aktuellesSpielbrett(request);
    }

}
