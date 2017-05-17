package com.iks.dddschach.domain;

import com.iks.dddschach.domain.base.EnumObject;
import com.iks.dddschach.domain.base.ValueObject;


/**
 * Eine Schachfigur repräsentiert durch den Typ (Bauer, Turm, etc.) und deren Farbe (schwarz, weiß)
 */
public class Spielfigur extends ValueObject {

    /**
     * Typ einer Schachfigur (Läufer, Springer, Dame, etc.)
     */
    public enum FigurenTyp implements EnumObject<FigurenTyp, Character> {

        /** König */
        KOENIG,
        /** Dame */
        DAME,
        /** Turm */
        TURM,
        /** Läufer  */
        LAEUFER,
        /** Springer bzw. Pferd */
        SPRINGER,
        /** Bauer */
        BAUER;

        public Character marshal() {
            switch (this) {
                case KOENIG:  return 'K';
                case DAME:    return 'Q';
                case TURM:    return 'R';
                case LAEUFER: return 'B';
                case SPRINGER: return 'N';
                case BAUER:   return 'P';
            }
            throw new IllegalArgumentException("Unexpected enum " + this);
        }

        public FigurenTyp unmarshal(Character encoded) {
            switch (encoded) {
                case 'K': return KOENIG;
                case 'Q': return DAME;
                case 'R': return TURM;
                case 'B': return LAEUFER;
                case 'N': return SPRINGER;
                case 'P': return BAUER;
            }
            throw new IllegalArgumentException("Unexpected marshal character " + this);
        }

    };

    /**
     * Typ der Spielfigur (Dame, König, etc.)
     */
    public final FigurenTyp figure;

    /**
     * Farbe der Spielfigur (schwarz, weiß)
     */
    public final Farbe color;


    public Spielfigur() {
        this(null, null);
    }


    public Spielfigur(FigurenTyp figure, Farbe color) {
        this.figure = figure;
        this.color = color;
    }


    public String abbreviation() {
        return "" + figure.marshal() + color.marshal();
    }

}
