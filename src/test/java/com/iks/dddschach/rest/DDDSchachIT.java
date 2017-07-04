package com.iks.dddschach.rest;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;


/**
 * Created by vollmer on 04.07.17.
 */
public class DDDSchachIT {

    private static String dockerHost;
    private static String dockerPort;
    private static WebTarget webTarget;

    @BeforeClass
    public static void init() {
        String host = System.getProperty("docker.container.ip");
        String port = System.getProperty("docker.container.port");
        dockerHost = (host == null)? "localhost" : host;
        dockerPort = (port == null)? "8080" : port;
        webTarget = ClientBuilder.newClient()
                .target("http://localhost:" + dockerPort + "/dddschach/api");
    }

    @Test
    public void isAlive() {
        final String property = System.getProperty("docker.container.ip");
        System.out.println("Hallo Test: " + dockerHost + ":" + dockerPort);
        final Response response = webTarget.path("isalive").request().get();
        Assert.assertEquals(200, response.getStatus());
    }

}
