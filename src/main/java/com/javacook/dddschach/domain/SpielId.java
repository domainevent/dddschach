package com.javacook.dddschach.domain;

import com.javacook.dddschach.domain.base.ValueObject;

import java.util.UUID;

public class SpielId extends SpielId0 implements ValueObject {

    public SpielId() {
        this(UUID.randomUUID().toString());
    }

    public SpielId(String id) {
       super(id);
    }

    public SpielId(SpielId0 spielId) {
        this(spielId.id);
    }

    @Override
    public String toString() {
        return id;
    }

}
