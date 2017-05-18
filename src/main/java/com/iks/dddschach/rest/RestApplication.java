package com.iks.dddschach.rest;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.iks.dddschach.api.SchachpartieApi;
import com.iks.dddschach.api.SchachpartieApiImpl;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;


@ApplicationPath("api")
public class RestApplication extends ResourceConfig {

    private SchachpartieApi schachpartieApi;

    public RestApplication() {
        register(new AbstractBinder() {
            protected void configure() {
                bind(new SchachpartieApiImpl()).to(SchachpartieApi.class);
            }
        });
        register(new JacksonJsonProvider().configure(SerializationFeature.INDENT_OUTPUT, true));
        packages("com.iks.dddschach.rest");
    }

}