package com.iks.dddschach.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iks.dddschach.api.SchachpartieApi;
import com.iks.dddschach.api.SchachpartieApi.UngueltigeSpielIdException;
import com.iks.dddschach.domain.Halbzug;
import com.iks.dddschach.domain.SpielId;

import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import java.io.IOException;


/**
 * Created by vollmer on 05.07.17.
 */
public class RestServiceClient implements RestServiceInterface {

    public static class RestCallFailedException extends RuntimeException {
        public final int statusCode;
        public final String content;

        public RestCallFailedException(int statusCode, String content) {this.statusCode = statusCode;
            this.content = content;
        }
    }

    private final static Client CLIENT = ClientBuilder.newClient();
    private final WebTarget webTarget;

    RestServiceClient() {
        final String host = System.getProperty("docker.container.ip");
        final String port = System.getProperty("docker.container.port");
        final String dockerHost = (host == null)? "localhost" : host;
        final String dockerPort = (port == null)? "8080" : port;
        // System.out.println("Docker-Host: " + dockerHost + ":" + dockerPort);
        webTarget = CLIENT.target("http://localhost:" + dockerPort + "/dddschach/api");
    }


    @Override
    public String isAlive() {
        final Response response = webTarget.path("isalive").request().get();
        return handleResponse(response, String.class);
    }

    @Override
    public SpielId neuesSpiel(String vermerk) {
        final Form note = new Form("note", vermerk);
        final Response response = webTarget.path("games").request().post(Entity.form(note));
        return handleResponse(response, SpielId.class);
    }

    @Override
    public Response spielbrett(String spielId, String clientId, Request request)
            throws UngueltigeSpielIdException {
        final Response response = webTarget.path("games").path(spielId).path("board").request().get();
        final int status = response.getStatus();
        switch (status) {
            case 200: return response;
            case 404: throw new UngueltigeSpielIdException(new SpielId(spielId));
            case 500: throw new InternalServerErrorException();
            default: throw new RestCallFailedException(status, response.readEntity(String.class));
        }
    }

    @Override
    public Response fuehreHalbzugAus(String spielId, String halbzug) throws SchachpartieApi.UngueltigerHalbzugException, UngueltigeSpielIdException {
        return null;
    }

    @Override
    public Response fuehreHalbzugAus(String spielId, Halbzug halbzug) throws SchachpartieApi.UngueltigerHalbzugException, UngueltigeSpielIdException {
        return null;
    }



    private <T> T handleResponse(Response response, Class<T> cl) {
        final int status = response.getStatus();
        final String content = response.readEntity(String.class);
        if (status != 200) {
            throw new RestCallFailedException(status, content);
        }
        return unmarshal(content, cl);
    }

    private Response handleResponse(Response response) {
        final int status = response.getStatus();
        final String content = response.readEntity(String.class);
        if (status != 200) {
            throw new RestCallFailedException(status, content);
        }
        return response;
    }

    private <T> T unmarshal(String str, Class<T> cl) {
        if (cl == String.class) return (T)str;
        try {
            return new ObjectMapper().readValue(str, cl);
        }
        catch (IOException e) {
            throw new RuntimeException("Could not unmarshal '"+str+"' to object of "+cl, e);
        }
    }
}
