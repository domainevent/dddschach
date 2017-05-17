package com.iks.dddschach.domain;

import com.iks.dddschach.domain.base.ValueObject;

import java.util.UUID;


/**
 * Eine universelle (weltweit) eindeutige Id eines Schachspiels.
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
