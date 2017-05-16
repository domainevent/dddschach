package com.iks.dddschach.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * Eine Schachfigur repräsentiert durch den Typ (Bauer, Turm, etc.) und deren Farbe (schwarz, weiß)
 */
@XmlType
public class Spielfigur extends ValueObject {

    /**
     * Typ einer Schachfigur (Läufer, Springer, Dame, etc.)
     */
    @XmlEnum
    public enum FigureEnum {
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

        @JsonValue
        public Character abbreviation() {
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
        public FigureEnum fromAbbrev(Character c) {
            switch (c) {
                case 'K': return KOENIG;
                case 'Q': return DAME;
                case 'R': return TURM;
                case 'B': return LAEUFER;
                case 'N': return SPRINGER;
                case 'P': return BAUER;
            }
            throw new IllegalArgumentException("Unexpected abbreviation character " + this);
        }

    };

    /**
     * Typ der Spielfigur (Dame, König, etc.)
     */
    public final FigureEnum figure;

    /**
     * Farbe der Spielfigur (schwarz, weiß)
     */
    public final FarbeEnum color;


    public Spielfigur() {
        this(null, null);
    }


    public Spielfigur(FigureEnum figure, FarbeEnum color) {
        this.figure = figure;
        this.color = color;
    }


    public String abbreviation() {
        return "" + figure.abbreviation() + color.abbreviation();
    }

}
