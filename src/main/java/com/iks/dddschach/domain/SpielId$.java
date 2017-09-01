package com.iks.dddschach.domain;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.iks.dddschach.domain.base.ValueObject;

import java.util.UUID;

//@JsonTypeInfo(
//        use = JsonTypeInfo.Id.NAME,
//        include = JsonTypeInfo.As.PROPERTY,
//        property = "@class")
//@JsonSubTypes({ @JsonSubTypes.Type(value = SpielId$.class) })
public class SpielId$ extends SpielId implements ValueObject {

    public SpielId$() {
        this(UUID.randomUUID().toString());
    }

    public SpielId$(String id) {
       super(id);
    }

    public SpielId$(SpielId spielId) {
        this(spielId.id);
    }
}
