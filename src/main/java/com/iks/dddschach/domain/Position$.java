package com.iks.dddschach.domain;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.iks.dddschach.domain.base.ValueObject;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "@class")
@JsonSubTypes({ @JsonSubTypes.Type(value = Position$.class) })
public class Position$ extends Position implements ValueObject {

    public Position$() {
    }

    public Position$(Position position) {
        this(position.spalte, position.zeile);
    }

    public Position$(Spalte spalte, Zeile zeile) {
        super(spalte, zeile);
    }
}
