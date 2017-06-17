package com.iks.dddschach.rest;

import com.iks.dddschach.api.SchachpartieApi;
import com.iks.dddschach.api.SchachpartieApi.UngueltigeSpielIdException;
import com.iks.dddschach.api.SchachpartieApi.UngueltigerHalbzugException;
import com.iks.dddschach.domain.Halbzug;
import com.iks.dddschach.domain.SpielId;
import com.iks.dddschach.domain.Spielbrett;
import com.webcohesion.enunciate.metadata.rs.ResponseCode;
import com.webcohesion.enunciate.metadata.rs.StatusCodes;
import org.apache.log4j.Logger;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;


/**
 * Dieser REST-Service stellt die Möglichkeit bereit, eine Schach-Partie "remote" über das Netzwerk
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
    @StatusCodes({
            @ResponseCode(code = 200, condition = "ok"),
            @ResponseCode(code = 500, condition = "An exception occured")
    })
    public SpielId neuesSpiel(@FormParam("note") String vermerk) {
        try {
            final SpielId spielId = schachpartieApi.neuesSpiel(Optional.ofNullable(vermerk));
            log.info("SpielId=" + spielId + ": (neu erzeugt) mit Vermerk '" + vermerk + "'");
            return spielId;
        }
        catch (Exception e) {
            log.error("Interner Server-Error", e);
            throw new InternalServerErrorException(e);
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
            @ResponseCode(code = 404, condition = "The game id is not valid"),
            @ResponseCode(code = 500, condition = "An exception occured")
    })
    public Response spielbrett(final @NotNull @PathParam("gameId") String spielId,
                                 final @Context Request request) throws UngueltigeSpielIdException {
        log.info("SpielId=" + spielId + ": Abfrage des Spielfeldes");

        try {
            final Spielbrett spielbrett = schachpartieApi.aktuellesSpielbrett(new SpielId(spielId));
            CacheControl cc = new CacheControl();
            cc.setMaxAge(-1);
            EntityTag etag = new EntityTag(""+ spielbrett.hashCode());
            Response.ResponseBuilder builder = request.evaluatePreconditions(etag);

            if (builder == null) {
                // nothing cached found, so transfer board again
                builder = Response.ok(spielbrett);
                builder.tag(etag);
            }
            // elsewhere a status vode 304 (NOT MODIFIED) ist produced
            builder.cacheControl(cc);
            return builder.build();
        }
        catch (UngueltigeSpielIdException e) {
            log.warn("SpielId=" + spielId + ": Die Spiel-ID '" + e.spielId + "' ist ungültig.");
            throw e;
        }
        catch (Exception e) {
            log.error("Interner Server-Error", e);
            throw new InternalServerErrorException(e);
        }
    }// aktuellesSpielbrett


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
    @StatusCodes({
            @ResponseCode(code = 200, condition = "ok"),
            @ResponseCode(code = 400, condition = "Missing or invalid half-move parameter"),
            @ResponseCode(code = 404, condition = "The game id is not valid"),
            @ResponseCode(code = 422, condition = "The half-move is not valid"),
            @ResponseCode(code = 500, condition = "An exception occured")
    })
    public Response fuehreHalbzugAus(
            final @NotNull @PathParam("gameId") String spielId,
            final @NotNull @FormParam("move") String halbzug)
            throws UngueltigerHalbzugException, UngueltigeSpielIdException {

        if (halbzug == null) {
            log.warn("SpielId=" + spielId + ": Der Parameter move fehlt.");
            throw new BadRequestException("Missing form parameter move");
        }
        try {
            return fuehreHalbzugAus(spielId, schachpartieApi.parse(halbzug));
        }
        catch (ParseException e) {
            throw new BadRequestException("Eingabe-Formatfehler: " + halbzug);
        }
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
    @StatusCodes({
            @ResponseCode(code = 200, condition = "ok"),
            @ResponseCode(code = 400, condition = "Missing or invalid half-move parameter"),
            @ResponseCode(code = 404, condition = "The game id is not valid"),
            @ResponseCode(code = 422, condition = "The half-move is not valid"),
            @ResponseCode(code = 500, condition = "An exception occured")
    })
    public Response fuehreHalbzugAus(
            final @NotNull @PathParam("gameId") String spielId,
            final @NotNull Halbzug halbzug) throws UngueltigerHalbzugException, UngueltigeSpielIdException {

        log.info("SpielId=" + spielId + ": Ausfuehren des Halbzuges " + halbzug);

        final int zugIndex;
        try {
            zugIndex = schachpartieApi.fuehreHalbzugAus(new SpielId(spielId), halbzug);
        }
        catch (UngueltigeSpielIdException e) {
            log.warn("SpielId=" + spielId + ": Die Spiel-ID '" + e.spielId + "' ist ungültig.");
            throw e;
        }
        catch (UngueltigerHalbzugException e) {
            log.debug("SpielId=" + spielId + ": Der Halbzug " + halbzug + " ist ungültig.");
            throw e;
        }
        catch (Exception e) {
            log.error("Interner Server-Error", e);
            throw new InternalServerErrorException(e);
        }

        log.debug("SpielId=" + spielId + ": Der " + zugIndex + ". Halbzug " + halbzug + " war erfolgreich.");

        // Erzeugen der JSON-Antwort und des Location-Headers:
        @SuppressWarnings("serial")
        HashMap<String, Object> json = new HashMap<String, Object>() {{
            put("index", zugIndex);
        }};
        UriBuilder ub = uriInfo.getAbsolutePathBuilder();
        URI location = ub.path("" + zugIndex).build();
        return Response.created(location).entity(json).build();
    }// fuehreHalbzugAus

}
