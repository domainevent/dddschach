package com.iks.dddschach.domain;

import com.iks.dddschach.domain.base.ValueObject;

import java.util.UUID;


public class SpielId$ extends SpielId implements ValueObject {

    public SpielId$() {
        this(UUID.randomUUID().toString());
    }

    public SpielId$(String id) {
       super(id);
    }
}
