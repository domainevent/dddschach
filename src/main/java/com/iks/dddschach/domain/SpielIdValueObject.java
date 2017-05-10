package com.iks.dddschach.domain;

import java.util.UUID;


/**
 * Created by vollmer on 05.05.17.
 */
public class SpielIdValueObject extends ValueObject {

    public final String id;


    public SpielIdValueObject(String id) {
        this.id = id;
    }

    public SpielIdValueObject() {
        this(UUID.randomUUID().toString());
    }


    @Override
    public String toString() {
        return id;
    }

}
