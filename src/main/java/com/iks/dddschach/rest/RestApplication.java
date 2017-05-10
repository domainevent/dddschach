package com.iks.dddschach.rest;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.iks.dddschach.api.SchachspielApi;
import com.iks.dddschach.api.SchachspielApiImpl;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;


@ApplicationPath("dddschach")
public class RestApplication extends ResourceConfig {

    private SchachspielApi schachspielApi;

    public RestApplication() {
        register(new AbstractBinder() {
            protected void configure() {
                bind(new SchachspielApiImpl()).to(SchachspielApi.class);
            }
        });
        register(new JacksonJsonProvider().configure(SerializationFeature.INDENT_OUTPUT, true));
        packages("com.iks.dddschach.rest");
    }

}