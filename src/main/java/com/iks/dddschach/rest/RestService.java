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

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
public class RestService implements RestServiceInterface {

    @Context
    SchachpartieApi schachpartieApi;

    @Context
    UriInfo uriInfo;

    final static Logger LOG = Logger.getLogger(RestService.class);


    /**
     * Ueberprueft, ob die Anwendung aktiv ist
     *
     * @return Eine Bestätigungsmeldung mit aktuellem Datum und Uhrzeit
     */
    @Override
    @GET
    @Path("isalive")
    @Produces(MediaType.TEXT_PLAIN)
    public String isAlive() {
        LOG.info("DDD-Schach lebt");
        return "DDD-Schach is alive: " + new Date();
    }


    /**
     * Erzeugt eine neue Schachpartie
     *
     * @param vermerk Vermerk zu diesem Spiel
     * @return Eine (weltweit) eindeutige Id dieses Spiels
     */
    @Override
    @POST
    @Path("games")
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    @StatusCodes({
            @ResponseCode(code = 200, condition = "ok"),
            @ResponseCode(code = 500, condition = "An exception occured")
    })
    public SpielId neuesSpiel(@Size(max = 100) @FormParam("note") String vermerk)
    {
        try {
            final SpielId spielId = schachpartieApi.neuesSpiel(Optional.ofNullable(vermerk));
            LOG.info("Neue Partie mit Spiel-ID='" + spielId + ", Vermerk='" + vermerk + "'");
            return spielId;
        }
        catch (Exception e) {
            LOG.error("Interner Server-Error", e);
            throw new InternalServerErrorException(e);
        }
    }


    /**
     * Liefert das Schachbrett, besser: die Informationen, welche Spielfiguren wo stehen.
     *
     * @return das Schachbrett einschließlich seiner Figuren
     */
    @Override
    @GET
    @Path("games/{gameId}/board")
    @Produces(MediaType.APPLICATION_JSON)
    @StatusCodes({
            @ResponseCode(code = 200, condition = "ok"),
            @ResponseCode(code = 404, condition = "The game id is not valid"),
            @ResponseCode(code = 500, condition = "An exception occured")
    })
    public Response spielbrett(final @PathParam("gameId") String spielId,
                               final @HeaderParam("clientId") String clientId,
                               final @Context Request request) throws UngueltigeSpielIdException
    {
        LOG.debug("Abfrage des Spielfeldes");
        try {
            final Spielbrett spielbrett = schachpartieApi.aktuellesSpielbrett(new SpielId(spielId));
            CacheControl cc = new CacheControl();
            cc.setMaxAge(60);
            EntityTag etag = new EntityTag(clientId + spielbrett.encode());
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
            LOG.warn("Die Spiel-ID '" + e.spielId + "' ist ungueltig.");
            throw e;
        }
        catch (Exception e) {
            LOG.error("Interner Server-Error", e);
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
    @Override
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
            final @PathParam("gameId") String spielId,
            final @NotNull(message = "The form parameter move is mandatory.")
            @FormParam("move") String halbzug)
            throws UngueltigerHalbzugException, UngueltigeSpielIdException
    {
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
    @Override
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
            final @PathParam("gameId") String spielId,
            final @NotNull(message = "A body of type Halbzug is required.") @Valid Halbzug halbzug)
            throws UngueltigerHalbzugException, UngueltigeSpielIdException
    {
        LOG.info("Ausfuehren des Halbzuges " + halbzug);

        final int zugIndex;
        try {
            zugIndex = schachpartieApi.fuehreHalbzugAus(new SpielId(spielId), halbzug);
        }
        catch (UngueltigeSpielIdException e) {
            LOG.warn("Die Spiel-ID '" + e.spielId + "' ist ungueltig.");
            throw e;
        }
        catch (UngueltigerHalbzugException e) {
            LOG.debug("Der Halbzug " + halbzug + " ist ungueltig.");
            throw e;
        }
        catch (Exception e) {
            LOG.error("Interner Server-Error", e);
            throw new InternalServerErrorException(e);
        }

        LOG.debug("Der " + zugIndex + ". Halbzug " + halbzug + " war erfolgreich.");

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
