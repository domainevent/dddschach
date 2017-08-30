package com.iks.dddschach.domain;

import com.iks.dddschach.domain.base.ValueObject;


/**
 * Eine Schachfigur repräsentiert durch den Typ (Bauer, Turm, etc.) und deren Farbe (schwarz, weiß)
 */
public class Spielfigur$ extends Spielfigur implements ValueObject {

    public Spielfigur$() {
    }


    public Spielfigur$(FigurenTyp figur, Farbe farbe) {
        super(figur, farbe);
    }
}
