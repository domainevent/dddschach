package com.iks.dddschach.domain;

import java.util.UUID;


/**
 * Created by vollmer on 05.05.17.
 */
public class SpielId extends ValueObject {

    public final String id;


    public SpielId(String id) {
        this.id = id;
    }

    public SpielId() {
        this(UUID.randomUUID().toString());
    }


    @Override
    public String toString() {
        return id;
    }

}
