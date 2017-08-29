package com.iks.dddschach.domain;

import java.util.UUID;


public class SpielId$ extends SpielId {

    public SpielId$() {
        this(UUID.randomUUID().toString());
    }

    public SpielId$(String id) {
       super(id);
    }
}
