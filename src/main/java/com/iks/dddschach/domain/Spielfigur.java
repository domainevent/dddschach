package com.iks.dddschach.domain;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * Eine Schachfigur repräsentiert durch den Typ (Bauer, Turm, etc.) und deren Farbe (schwarz, weiß)
 */
@XmlType
public class Spielfigur extends ValueObject {

    /**
     * Typ einer Schachfigur (Läufer, Pferd, etc.)
     */
    @XmlEnum
    public enum FigureEnum {
        KOENIG, DAME, TURM, LAEUFER, PFERD, BAUER;

        public Character abbreviation() {
            switch (this) {
                case KOENIG: return 'K';
                case DAME: return 'D';
                case TURM: return 'T';
                case LAEUFER: return 'L';
                case PFERD: return 'P';
                case BAUER: return 'B';
            }
            throw new IllegalArgumentException("Unexpected enum " + this);
        }
    };


    public final FigureEnum figure;
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
