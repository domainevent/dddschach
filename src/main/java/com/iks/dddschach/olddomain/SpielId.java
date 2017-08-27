package com.iks.dddschach.olddomain;

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

    public static SpielId fromNew(com.iks.dddschach.domain.SpielId spielId) {
        return new SpielId(spielId.getId());
    }

}
