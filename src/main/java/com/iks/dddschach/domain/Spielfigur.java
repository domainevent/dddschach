package com.iks.dddschach.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.iks.dddschach.domain.base.EnumObject;
import com.iks.dddschach.domain.base.ValueObject;


/**
 * Eine Schachfigur repräsentiert durch den Typ (Bauer, Turm, etc.) und deren Farbe (schwarz, weiß)
 */
public class Spielfigur extends ValueObject {

    /**
     * Typ einer Schachfigur (Läufer, Springer, Dame, etc.)
     */
    public enum FigurenTyp implements EnumObject<Character> {

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

        @Override
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

        @JsonCreator
        public static FigurenTyp unmarshal(Character encoded) {
            switch (encoded) {
                case 'K': return KOENIG;
                case 'Q': return DAME;
                case 'R': return TURM;
                case 'B': return LAEUFER;
                case 'N': return SPRINGER;
                case 'P': return BAUER;
            }
            throw new IllegalArgumentException("Unexpected marshalled character " + encoded);
        }
    }


    /**
     * Typ der Spielfigur (Dame, König, etc.)
     */
    public final FigurenTyp figurTyp;

    /**
     * Farbe der Spielfigur (schwarz, weiß)
     */
    public final Farbe farbe;


    /**
     * Konstruktor
     * @param figurTyp Spielfigur-Typ
     * @param farbe Spielfigur-Farbe
     */
    public Spielfigur(FigurenTyp figurTyp, Farbe farbe) {
        this.figurTyp = figurTyp;
        this.farbe = farbe;
    }

    @SuppressWarnings("unused")
    private Spielfigur() {
        this(null, null);
    }

    @Override
    public String toString() {
        return "" + figurTyp.marshal() + farbe.marshal();
    }

}
