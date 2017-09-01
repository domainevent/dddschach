package com.iks.dddschach.service.binding_rest;

import com.iks.dddschach.domain.*;
import com.iks.dddschach.service.api.SchachpartieApi;
import com.webcohesion.enunciate.metadata.rs.ResponseCode;
import com.webcohesion.enunciate.metadata.rs.StatusCodes;
import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;


/**
 * Dieser REST-Service stellt die Möglichkeit bereit, eine Schach-Partie "remote" über das Netzwerk
 * zu spielen.
 */
@Path("/ssd")
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
    public NeuesSpielResponse neuesSpiel(NeuesSpielRequest request) throws Exception
    {
        try {
            final NeuesSpielResponse response = schachpartieApi.neuesSpiel(request);
            LOG.info("Neue Partie mit Spiel-ID='" + response.getSpielId() + ", Vermerk='" + request.getVermerk() + "'");
            return response;
        }
        catch (Exception e) {
            LOG.error("Interner Server-Error", e);
            throw new InternalServerErrorException(e);
        }
    }


    @Override
    public Halbzug$ parse(String eingabe) throws ParseException
    {
        return null;
    }


    @Override
    @POST
    @Path("fuehreHalbzugAus")
    @StatusCodes({
            @ResponseCode(code = 200, condition = "ok"),
            @ResponseCode(code = 400, condition = "Input validation error"),
            @ResponseCode(code = 500, condition = "An exception occured")})
    public FuehreHalbzugAusResponse fuehreHalbzugAus(FuehreHalbzugAusRequest request)
            throws UngueltigerHalbzugException, UngueltigeSpielIdException, IOException {
        return schachpartieApi.fuehreHalbzugAus(request);
    }


    @Override
    @POST
    @Path("aktuellesSpielbrett")
    @StatusCodes({
            @ResponseCode(code = 200, condition = "ok"),
            @ResponseCode(code = 400, condition = "Input validation error"),
            @ResponseCode(code = 500, condition = "An exception occured")})
    public AktuellesSpielbrettResponse aktuellesSpielbrett(AktuellesSpielbrettRequest request)
            throws UngueltigeSpielIdException, IOException {
//        return new AktuellesSpielbrettResponse(SpielbrettFactory.createInitialesSpielbrett());
        return schachpartieApi.aktuellesSpielbrett(request);
    }
}
