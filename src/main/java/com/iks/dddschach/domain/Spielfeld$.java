package com.iks.dddschach.domain;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;


@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "@class")
@JsonSubTypes({ @JsonSubTypes.Type(value = Spielfeld$.class) })
public class Spielfeld$ extends Spielfeld {

    public Spielfeld$() {
    }

    public Spielfeld$(Position$ position, Spielfigur$ spielfigur) {
        super(position, spielfigur);
    }

    @Override
    public Spielfigur$ getSpielfigur() {
        return (Spielfigur$)spielfigur;
    }

//    public void setPosition(Position$ value) {
//        this.position = new Position$(value.getSpalte(), value.getZeile());
//    }

    @Override
    public Position$ getPosition() {
        return (Position$)position;
    }

}
