package com.iks.dddschach.rest;

import com.iks.dddschach.api.SchachpartieApi.UngueltigerHalbzugException;
import org.apache.log4j.Logger;
import com.iks.dddschach.api.SchachpartieApi;
import com.iks.dddschach.domain.Spielbrett;
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
import java.util.Optional;


/**
 * Dieser Service stellt die Möglichkeit bereit, eine Schach-Partie "remote" über das Netzwerk
 * zu spielen.
 */
@Path("/")
public class RestService {

    @Context
    SchachpartieApi schachpartieApi;

    @Context
    UriInfo uriInfo;

    final Logger log = Logger.getLogger(RestService.class);


    /**
     * Ueberprueft, ob die Anwendung aktiv ist
     *
     * @return Eine Bestätigungsmeldung mit aktuellem Datum und Uhrzeit
     */
    @GET
    @Path("isalive")
    @Produces(MediaType.TEXT_PLAIN)
    public String isAlive() {
        log.info("DDD-Schach lebt");
        return "DDD-Schach is alive: " + new Date();
    }


    /**
     * Erzeugt eine neue Schachpartie
     *
     * @param vermerk Vermerk zu diesem Spiel
     * @return Eine (weltweit) eindeutige Id dieses Spiels
     */
    @POST
    @Path("games")
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public SpielId neuesSpiel(@FormParam("note") String vermerk) {
        try {
            final SpielId spielId = schachpartieApi.neuesSpiel(Optional.ofNullable(vermerk));
            log.info("SpielId=" + spielId + ": (neu erzeugt) mit Vermerk '" + vermerk + "'");
            return spielId;
        }
        catch (Exception e) {
            log.error("Interner Server-Error", e);
            throw new ServerErrorException(Response.Status.INTERNAL_SERVER_ERROR, e);
        }
    }


    /**
     * Liefert das Schachbrett, besser: die Informationen, welche Spielfiguren wo stehen.
     *
     * @return das Schachbrett einschließlich seiner Figuren
     */
    @GET
    @Path("games/{gameId}/board")
    @Produces(MediaType.APPLICATION_JSON)
    @StatusCodes({
            @ResponseCode(code = 200, condition = "ok"),
            @ResponseCode(code = 404, condition = "The field at the given coordinates is empty"),
            @ResponseCode(code = 500, condition = "An exception occured")
    })
    public Spielbrett spielbrett(final @NotNull @PathParam("gameId") String spielId) {
        log.info("SpielId=" + spielId + ": Abfrage des Spielfeldes");

        try {
            return schachpartieApi.spielbrett(new SpielId(spielId));
        }
        catch (Exception e) {
            log.error("Interner Server-Error", e);
            throw new ServerErrorException(Response.Status.INTERNAL_SERVER_ERROR, e);
        }
    }// spielbrett


    /**
     * Fuehrt einen Schach-Halbzug innerhalb der Schachpartie mit der Id <code>spielId</code> aus.
     * Der Halbzug hat die Syntax [A-Ha-h]-[1-8], Beispiel: "b1-c3"
     *
     * @param halbzug ein Schach-Halbzug repräsentiert als String
     * @return {@link Response} enthält den Index des Halbzuges in der Form z.B.
     * <pre>
     *     {
     *         "index":3
     *     }
     * </pre>
     */
    @POST
    @Path("games/{gameId}/moves")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    @ManagedAsync
    public Response fuehreHalbzugAus(
            final @NotNull @PathParam("gameId") String spielId,
            final @NotNull @FormParam("move") String halbzug) throws UngueltigerHalbzugException {

        if (halbzug == null) {
            log.warn("SpielId=" + spielId + ": Der Parameter move fehlt.");
            throw new BadRequestException("Missing form parameter move");
        }
        return fuehreHalbzugAus(spielId, new Halbzug(halbzug));
    }


    /**
     * Fuehrt einen Schach-Halbzug innerhalb der Schachpartie mit der Id <code>spielId</code> aus.
     *
     * @param halbzug der auszufuehrende Halbzug
     * @return {@link Response} enthält den Index des Halbzuges in der Form z.B.
     * <pre>
     *     {
     *         "index":3
     *     }
     * </pre>
     */
    @POST
    @Path("games/{gameId}/moves")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response fuehreHalbzugAus(
            final @NotNull @PathParam("gameId") String spielId,
            final @NotNull Halbzug halbzug) throws UngueltigerHalbzugException {

        log.info("SpielId=" + spielId + ": Ausfuehren des Halbzuges " + halbzug);

        final int zugIndex;
        try {
            zugIndex = schachpartieApi.fuehreHalbzugAus(new SpielId(spielId), halbzug);
        }
        catch (UngueltigerHalbzugException e) {
            log.debug("SpielId=" + spielId + ": Der Halbzug " + halbzug + " ist ungültig.");
            throw e;
        }
        catch (Exception e) {
            log.error("Interner Server-Error", e);
            throw new ServerErrorException(Response.Status.INTERNAL_SERVER_ERROR, e);
        }

        log.debug("SpielId=" + spielId + ": Der " + zugIndex + ". Halbzug " + halbzug + " war erfolgreich.");

        // Erzeugen der JSON-Antwort und des Location-Headers:
        HashMap<String, Object> json = new HashMap<String, Object>() {{
            put("index", zugIndex);
        }};
        UriBuilder ub = uriInfo.getAbsolutePathBuilder();
        URI location = ub.path("" + zugIndex).build();
        return Response.created(location).entity(json).build();
    }// fuehreHalbzugAus

}
