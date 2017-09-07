package com.javacook.dddschach.service.binding_rest;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.javacook.dddschach.service.api.SchachpartieApi;
import com.javacook.dddschach.service.impl.SchachpartieApiImpl;
import com.javacook.dddschach.persistence.SchachpartieRepositoryDB;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;


@ApplicationPath("api")
public class RestApplication extends ResourceConfig {

    public RestApplication() {
        register(new AbstractBinder() {
            protected void configure() {
                bind(new SchachpartieApiImpl(new SchachpartieRepositoryDB())).to(SchachpartieApi.class);
            }
        });
        register(new JacksonJsonProvider().configure(SerializationFeature.INDENT_OUTPUT, true));
        packages("com.javacook.dddschach.rest,com.javacook.dddschach.service.binding_rest");
    }

}