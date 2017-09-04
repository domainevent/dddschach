package com.iks.dddschach.service.binding_rest_client;

import com.google.gson.Gson;
import com.iks.dddschach.domain.*;
import com.iks.dddschach.domain.validation.Zugregel;
import com.iks.dddschach.service.api.SchachpartieApi;

import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.*;
import java.io.IOException;
import java.util.Map;


/**
 * Created by vollmer on 05.07.17.
 */
public class SchachpartieRestClient implements SchachpartieApi {

    public String isAlive() {
        final Response response = webTarget.path("isalive").request().get();
        return handleResponse(response, String.class);
    }


    @Override
    public NeuesSpielResponse neuesSpiel(NeuesSpielRequest request) throws Exception {
        final Response response = webTarget
                .path("ssd").path("neuesSpiel")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(request, MediaType.APPLICATION_JSON_TYPE));
        return handleResponse(response, NeuesSpielResponse.class);
    }



    @Override
    public FuehreHalbzugAusResponse fuehreHalbzugAus(FuehreHalbzugAusRequest request) throws UngueltigerHalbzugException, UngueltigeSpielIdException, IOException {
        final Halbzug halbzug = request.getHalbzug();
        final SpielId spielId = request.getSpielId();
        final Response response = webTarget
                .path("ssd").path("fuehreHalbzugAus")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(request, MediaType.APPLICATION_JSON_TYPE));

        final int status = response.getStatus();
        switch (status) {
            case 201: return response.readEntity(FuehreHalbzugAusResponse.class);
            case 400: throw new RestCallFailedException(status, response.readEntity(String.class));
            case 422:
                final Map<String, Object> json = response.readEntity(new GenericType<Map<String, Object>>() {});
                final String verletzteZugregel = (String)json.get(json.get(json.get("error code")));
                throw new UngueltigerHalbzugException(halbzug, Zugregel.valueOf(verletzteZugregel));
            case 404: throw new UngueltigeSpielIdException(new SpielId$(spielId));
            case 500: throw new InternalServerErrorException();
            default: throw new RestCallFailedException(status, response.readEntity(String.class));
        }
    }



    @Override
    public AktuellesSpielbrettResponse aktuellesSpielbrett(AktuellesSpielbrettRequest request) throws UngueltigeSpielIdException, IOException {
        final SpielId spielId = request.getSpielId();
        final Response response = webTarget
                .path("ssd").path("aktuellesSpielbrett")
                .request(MediaType.APPLICATION_JSON)
                .header("clientId", request.getClientId())
                .get();

        final int status = response.getStatus();
        switch (status) {
            case 200:
            case 304: return response.readEntity(AktuellesSpielbrettResponse.class);
            case 404: throw new UngueltigeSpielIdException(new SpielId$(spielId));
            case 500: throw new InternalServerErrorException();
            default: throw new RestCallFailedException(status, response.readEntity(String.class));
        }
    }









    public static class RestCallFailedException extends RuntimeException {
        public final int statusCode;
        public final String content;

        public RestCallFailedException(int statusCode, String content) {this.statusCode = statusCode;
            this.content = content;
        }
        @Override
        public String getMessage() {
            return "Status code: " + statusCode + ", hint: " + content;
        }
    }

    private final static Client CLIENT = ClientBuilder.newClient();
    private final WebTarget webTarget;

    SchachpartieRestClient()
    {
        final String host = System.getProperty("docker.container.ip");
        final String port = System.getProperty("docker.container.port");
        final String dockerHost = (host == null)? "localhost" : host;
        final String dockerPort = (port == null)? "8080" : port;
        // System.out.println("Docker-Host: " + dockerHost + ":" + dockerPort);
        webTarget = CLIENT.target("http://localhost:" + dockerPort + "/dddschach/api");
    }




    public SpielId neuesSpiel(String vermerk)
    {
        final Form note = new Form("note", vermerk);
        final Response response = webTarget.path("games").request().post(Entity.form(note));
        return handleResponse(response, SpielId.class);
    }

    public Response spielbrett(String spielId, String clientId, Request request)
            throws UngueltigeSpielIdException
    {
        final Response response = webTarget.path("games").path(spielId).path("board")
                .request(MediaType.APPLICATION_JSON)
                .header("clientId", clientId)
                .get();

        final int status = response.getStatus();
        switch (status) {
            case 200:
            case 304: return response;
            case 404: throw new UngueltigeSpielIdException(new SpielId$(spielId));
            case 500: throw new InternalServerErrorException();
            default: throw new RestCallFailedException(status, response.readEntity(String.class));
        }
    }


    /**
     * Eine zusätzliche Methode, mit der es möglich ist, eine etag-Wert zu setzen, der
     * in den Header geschrieben wird.
     */
    public Response spielbrettEtag(String spielId, String clientId, String etagValue)
            throws UngueltigeSpielIdException
    {
        final Response response = webTarget.path("games").path(spielId).path("board")
                .request(MediaType.APPLICATION_JSON)
                .header("clientId", clientId)
                .header("If-None-Match", '"'+etagValue+'"')
                .get();

        final int status = response.getStatus();
        switch (status) {
            case 200:
            case 304: return response;
            case 404: throw new UngueltigeSpielIdException(new SpielId$(spielId));
            case 500: throw new InternalServerErrorException();
            default: throw new RestCallFailedException(status, response.readEntity(String.class));
        }
    }

    public Response fuehreHalbzugAus(String spielId, String halbzug)
            throws UngueltigerHalbzugException, UngueltigeSpielIdException
    {
        final Form move = new Form("move", halbzug);
        final Response response = webTarget.path("games").path(spielId).path("moves")
                .request().post(Entity.form(move));
        final int status = response.getStatus();
        switch (status) {
            case 201: return response;
            case 400: throw new RestCallFailedException(status, response.readEntity(String.class));
            case 422:
                final Map<String, Object> json = response.readEntity(new GenericType<Map<String, Object>>() {});
                final String verletzteZugregel = (String)json.get(json.get(json.get("error code")));
                final Halbzug$ halbzug$ = SpielNotationParser.parse(halbzug);
                throw new UngueltigerHalbzugException(halbzug$, Zugregel.valueOf(verletzteZugregel));
            case 404: throw new UngueltigeSpielIdException(new SpielId$(spielId));
            case 500: throw new InternalServerErrorException();
            default: throw new RestCallFailedException(status, response.readEntity(String.class));
        }
    }


    public Response fuehreHalbzugAus(String spielId, Halbzug halbzug) throws UngueltigerHalbzugException, UngueltigeSpielIdException {
        return fuehreHalbzugAus(spielId, ((Halbzug$) halbzug).encode());
    }




    /*======================================================*\
     * Utils                                                *
    \*======================================================*/

    private <T> T handleResponse(Response response, Class<T> cl) {
        final int status = response.getStatus();
        final String content = response.readEntity(String.class);
        if (status != 200) {
            throw new RestCallFailedException(status, content);
        }
        return unmarshal(content, cl);
    }

//    private <T> T unmarshal(String str, Class<T> cl) {
//        if (cl == String.class) return (T)str;
//        try {
//            return new ObjectMapper().readValue(str, cl);
//        }
//        catch (IOException e) {
//            throw new RuntimeException("Could not unmarshal '"+str+"' to object of "+cl, e);
//        }
//    }


    private <T> T unmarshal(String str, Class<T> cl) {
        if (cl == String.class) return (T)str;
        return new Gson().fromJson(str, cl);
    }


    public static void main(String[] args) throws Exception {
        final SchachpartieRestClient client = new SchachpartieRestClient();
        final String alive = client.isAlive();
        System.out.println(alive);

        final NeuesSpielResponse neuesSpielResponse = client.neuesSpiel(new NeuesSpielRequest("Hallo Welt"));
        System.out.println(neuesSpielResponse.getSpielId());
        final SpielId$ spielId = (SpielId$) neuesSpielResponse.getSpielId();

        final AktuellesSpielbrettRequest aktuellesSpielbrettRequest = new AktuellesSpielbrettRequest(null, spielId);
        final AktuellesSpielbrettResponse aktuellesSpielbrettResponse = client.aktuellesSpielbrett(aktuellesSpielbrettRequest);
        System.out.println(neuesSpielResponse.getSpielId());




    }

}
