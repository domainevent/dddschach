package com.iks.dddschach.domain;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.iks.dddschach.domain.base.ValueObject;


/**
 * Eine Schachfigur repräsentiert durch den Typ (Bauer, Turm, etc.) und deren Farbe (schwarz, weiß)
 */
//@JsonTypeInfo(
//        use = JsonTypeInfo.Id.NAME,
//        include = JsonTypeInfo.As.PROPERTY,
//        property = "@class")
//@JsonSubTypes({ @JsonSubTypes.Type(value = Spielfigur$.class) })
public class Spielfigur$ extends Spielfigur implements ValueObject {

    public Spielfigur$() {
    }


    public Spielfigur$(FigurenTyp figur, Farbe farbe) {
        super(figur, farbe);
    }
}
