package com.iks.dddschach.rest;

import com.iks.dddschach.domain.*;
import com.iks.dddschach.service.api.SchachpartieApi;
import com.iks.dddschach.service.api.SchachpartieApi.UngueltigeSpielIdException;
import com.iks.dddschach.service.api.SchachpartieApi.UngueltigerHalbzugException;
import com.webcohesion.enunciate.metadata.rs.ResponseCode;
import com.webcohesion.enunciate.metadata.rs.StatusCodes;
import org.apache.log4j.Logger;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.IOException;
import java.net.URI;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;


/**
 * Dieser REST-Service stellt die Möglichkeit bereit, eine Schach-Partie "remote" über das Netzwerk
 * zu spielen.
 */
@Path("/new")
public class RestServiceNew implements SchachpartieApi {

    @Context
    SchachpartieApi schachpartieApi;

    @Context
    UriInfo uriInfo;

    final static Logger LOG = Logger.getLogger(RestServiceNew.class);


    /**
     * Ueberprueft, ob die Anwendung aktiv ist
     *
     * @return Eine Bestätigungsmeldung mit aktuellem Datum und Uhrzeit
     */
    @GET
    @Path("isalive")
    @Produces(MediaType.TEXT_PLAIN)
    public String isAlive() {
        LOG.info("DDD-Schach lebt");
        return "DDD-Schach is alive: " + new Date();
    }


    @Override
    @POST
    @Path("games")
    @StatusCodes({
            @ResponseCode(code = 200, condition = "ok"),
            @ResponseCode(code = 500, condition = "An exception occured")
    })
    public NeuesSpielResponse neuesSpiel(NeuesSpielRequest request) throws Exception {
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
    public Halbzug$ parse(String eingabe) throws ParseException {
        return null;
    }


    @Override
    public FuehreHalbzugAusResponse fuehreHalbzugAus(FuehreHalbzugAusRequest request) throws UngueltigerHalbzugException, UngueltigeSpielIdException, IOException {
        return null;
    }


    @Override
    public AktuellesSpielbrettResponse aktuellesSpielbrett(AktuellesSpielbrettRequest request) throws UngueltigeSpielIdException, IOException {
        return null;
    }
}
