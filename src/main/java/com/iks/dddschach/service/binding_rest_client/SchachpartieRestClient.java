package com.iks.dddschach.service.binding_rest_client;

import com.google.gson.Gson;
import com.iks.dddschach.domain.*;
import com.iks.dddschach.domain.validation.Zugregel;
import com.iks.dddschach.service.api.SchachpartieApi;
import com.iks.dddschach.service.api.UngueltigeSpielIdException;
import com.iks.dddschach.service.api.UngueltigerHalbzugException;

import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;


/**
 * Created by vollmer on 05.07.17.
 */
public class SchachpartieRestClient implements SchachpartieApi {


    private final static Client CLIENT = ClientBuilder.newClient();
    private final WebTarget webTarget;

    public SchachpartieRestClient()
    {
        final String host = System.getProperty("docker.container.ip");
        final String port = System.getProperty("docker.container.port");
        final String dockerHost = (host == null)? "localhost" : host;
        final String dockerPort = (port == null)? "8080" : port;
        // System.out.println("Docker-Host: " + dockerHost + ":" + dockerPort);
        webTarget = CLIENT.target("http://localhost:" + dockerPort + "/dddschach/api");
    }



    public String isAlive() {
        final Response response = webTarget.path("isalive").request().get();
        return handleResponse(response, String.class);
    }


    @Override
    public NeuesSpielResponse neuesSpiel(NeuesSpielRequest request) {
        final Response response = webTarget
                .path("neuesSpiel")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(request, MediaType.APPLICATION_JSON_TYPE));
        return handleResponse(response, NeuesSpielResponse.class);
    }



    @Override
    public FuehreHalbzugAusResponse fuehreHalbzugAus(FuehreHalbzugAusRequest request)
            throws UngueltigerHalbzugException, UngueltigeSpielIdException
    {
        final Halbzug halbzug = request.getHalbzug();
        final SpielId spielId = request.getSpielId();
        final Response response = webTarget
                .path("fuehreHalbzugAus")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(request, MediaType.APPLICATION_JSON_TYPE));

        final int status = response.getStatus();
        switch (status) {
            case 200:
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
    public AktuellesSpielbrettResponse aktuellesSpielbrett(AktuellesSpielbrettRequest request) throws UngueltigeSpielIdException
    {
        final SpielId spielId = request.getSpielId();
        final Response response = webTarget
                .path("aktuellesSpielbrett")
                .request(MediaType.APPLICATION_JSON)
                .header("clientId", request.getClientId())
                .post(Entity.entity(request, MediaType.APPLICATION_JSON_TYPE));

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
        final SpielId$ spielId = (SpielId$)neuesSpielResponse.getSpielId();

        final AktuellesSpielbrettRequest aktuellesSpielbrettRequest1 = new AktuellesSpielbrettRequest(null, spielId);
        final AktuellesSpielbrettResponse aktuellesSpielbrettResponse1 = client.aktuellesSpielbrett(aktuellesSpielbrettRequest1);
        System.out.println(aktuellesSpielbrettResponse1.getSpielbrett());

        Halbzug$ halbzug = new Halbzug$("b2-b4");
        final FuehreHalbzugAusRequest fuehreHalbzugAusRequest = new FuehreHalbzugAusRequest(spielId, halbzug);
        final FuehreHalbzugAusResponse fuehreHalbzugAusResponse = client.fuehreHalbzugAus(fuehreHalbzugAusRequest);
        System.out.println(fuehreHalbzugAusResponse.getHalbzugZaehler());

        final AktuellesSpielbrettRequest aktuellesSpielbrettRequest2 = new AktuellesSpielbrettRequest(null, spielId);
        final AktuellesSpielbrettResponse aktuellesSpielbrettResponse2 = client.aktuellesSpielbrett(aktuellesSpielbrettRequest2);
        System.out.println(aktuellesSpielbrettResponse2.getSpielbrett());

    }

}
