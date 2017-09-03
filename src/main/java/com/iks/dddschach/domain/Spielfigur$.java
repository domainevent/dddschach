package com.iks.dddschach.domain;

import com.iks.dddschach.domain.base.ValueObject;

import static com.iks.dddschach.domain.Farbe.WEISS;
import static java.lang.Character.toLowerCase;
import static java.lang.Character.toUpperCase;


/**
 * Eine Schachfigur repräsentiert durch den Typ (Bauer, Turm, etc.) und deren Farbe (schwarz, weiß)
 */
public class Spielfigur$ extends Spielfigur implements ValueObject {

    public Spielfigur$() {
    }

    public Spielfigur$(FigurenTyp figur, Farbe farbe) {
        super(figur, farbe);
    }


    public Spielfigur$(Spielfigur spielfigur) {
        this(spielfigur.figur, spielfigur.farbe);
    }


    public Character encodeFigurTyp(FigurenTyp figur) {
        switch (figur) {
            case KOENIG:  return 'K';
            case DAME:    return 'Q';
            case TURM:    return 'R';
            case LAEUFER: return 'B';
            case SPRINGER: return 'N';
            case BAUER:   return 'P';
        }
        throw new IllegalArgumentException("Unexpected enum " + this);
    }

    public Character encodeFarbe(Farbe farbe) {
        switch (farbe) {
            case WEISS:  return 'w';
            case SCHWARZ:    return 'b';
        }
        throw new IllegalArgumentException("Unexpected enum " + this);
    }

    public String encode() {
        final Character ch = encodeFigurTyp(figur);
        return String.valueOf((farbe == WEISS)? toUpperCase(ch) : toLowerCase(ch));
    }

    public String toString() {
        return "" + encodeFigurTyp(figur) + encodeFarbe(farbe);
    }

}
