package com.iks.dddschach.domain;

import java.util.UUID;


public class SpielIdExt extends SpielId {

    public SpielIdExt() {
        this(UUID.randomUUID().toString());
    }

    public SpielIdExt(String id) {
       super(id);
    }
}
