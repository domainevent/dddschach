package com.iks.dddschach.rest;

import org.apache.log4j.Logger;
import com.iks.dddschach.api.SchachspielApi;
import com.iks.dddschach.domain.FarbeEnum;
import com.iks.dddschach.domain.Schachbrett;
import com.iks.dddschach.domain.SpielId;
import com.iks.dddschach.domain.Halbzug;
import com.webcohesion.enunciate.metadata.rs.ResponseCode;
import com.webcohesion.enunciate.metadata.rs.StatusCodes;
import org.glassfish.jersey.server.ManagedAsync;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.Date;
import java.util.HashMap;


/**
 * Dieser Service dient dazu eine Schach-Partie "remote", d.h. uber das
 */
@Path("/")
public class RestService {

    @Context
    SchachspielApi schachspielApi;

    @Context
    UriInfo uriInfo;

    final Logger log = Logger.getLogger(RestService.class);


    /**
     * Ueberprueft, ob die Anwendung aktiv ist
     * @return eine Bestaetigungsmeldung mit aktuellem Datum und Uhrzeit
     */
    @GET
    @Path("isalive")
    @Produces(MediaType.TEXT_PLAIN)
    public String isAlive() {
        log.info("dddschach is alive");
        return "DDD-Schach is alive: " + new Date();
    }


    /**
     * Erzeugt ein neues Spiel
     * @param color FIXME: Irgendwie quatsch
     */
    @POST
    @Path("games")
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public SpielId neuesSpiel(@FormParam("color") FarbeEnum color) {
        log.info("Neues Spiel, Spielerfarbe " + color);

        try {
            return schachspielApi.neuesSpiel();
        }
        catch (Exception e) {
            // TODO: Detailiertere Fehlerbehandlung
            throw new ServerErrorException(Response.Status.INTERNAL_SERVER_ERROR, e);
        }
    }


    /**
     * Returns the figure at the position (<code>horCoord</code>, <code>vertCord</code>)
     * @return the chess figure at the given coordinates
     */
    @GET
    @Path("games/{gameId}/board")
    @Produces(MediaType.APPLICATION_JSON)
    @StatusCodes({
            @ResponseCode( code = 200, condition = "ok"),
            @ResponseCode( code = 404, condition = "The field at the given coordinates is empty"),
            @ResponseCode( code = 500, condition = "An exception occured")
    })
    public Schachbrett schachBrett(final @NotNull @PathParam("gameId") String spielId) {
        log.info("Spiel " + spielId +": Spielfeld mit Id " + spielId);

        try {
            return schachspielApi.schachBrett(new SpielId(spielId));
        }
        catch (Exception e) {
            // TODO: Detailiertere Fehlerbehandlung
            throw new ServerErrorException(Response.Status.INTERNAL_SERVER_ERROR, e);
        }
    }// schachBrett



    /**
     * Fuehrt einen Schach-Zug in der Schachpartie mit der Id <code>spielId</code> aus. Der Zug
     * hat die Syntax [A-Ha-h]-[1-8], Beispiel: "b1-c3"
     * @param zug ein Schach-Zug repraesentiert als String
     */
    @POST
    @Path("games/{gameId}/moves")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    @ManagedAsync
    public Response fuehreZugAus(
            final @NotNull @PathParam("gameId") String spielId,
            final @NotNull @FormParam("move") String zug) {

        log.info("Spiel " + spielId + ": Ausfuehren des Zuges " + zug);
        if (zug == null) {
            throw new BadRequestException("Missing form parameter 'move'");
        }
        return fuehreZugAus(spielId, new Halbzug(zug));
    }


    /**
     * Fuehrt einen Schach-Zug in der Schachpartie mit der Id <code>spielId</code> aus.
     * @param zug der auszufuehrende Zug
     */
    @POST
    @Path("games/{gameId}/moves")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ManagedAsync
    public Response fuehreZugAus(
            final @NotNull @PathParam("gameId") String spielId,
            final @NotNull Halbzug zug) {

        log.info("Spiel " + spielId + ": Ausfuehren des Zuges " + zug);

        final int zugIndex;
        try {
            zugIndex = schachspielApi.fuehreHalbzugAus(new SpielId(spielId), zug);
        }
        catch (Exception e) {
            // TODO: Detailiertere Fehlerbehandlung
            throw new ServerErrorException(Response.Status.INTERNAL_SERVER_ERROR, e);
        }

        log.info("Der " + zugIndex + ". Zug wurde erfolgreich ausgefuehrt.");

        // Erzeugen der JSON-Antwort und des Location-Headers:
        HashMap<String, Object> json = new HashMap<String, Object>() {{
            put("index", zugIndex);
        }};
        UriBuilder ub = uriInfo.getAbsolutePathBuilder();
        URI location = ub.path("" + zugIndex).build();
        return Response.created(location).entity(json).build();
    }// fuehreHalbzugAus

}
