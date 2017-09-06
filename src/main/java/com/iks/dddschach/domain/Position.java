package com.iks.dddschach.domain;


import com.iks.dddschach.domain.base.EnumObject;
import com.iks.dddschach.domain.base.ValueObject;

import javax.xml.bind.annotation.XmlEnum;


/**
 * Repräsentiert eine Schachbrett-Koordinate (z.B. b4)
 */
public class Position extends ValueObject {

    /**
     * Die horizontale Koordinate des Schachbretts (a-h)
     */
    @XmlEnum
    public enum Spalte implements EnumObject<Character> {
        A, B, C, D, E, F, G, H;

        @Override
        public Character marshal() {
            return name().toLowerCase().charAt(0);
        }

        public static Spalte unmarshal(Character encoded) {
            return valueOf(encoded.toString().toUpperCase());
        }

        @Override
        public String toString() {
            return marshal().toString();
        }
    }


    /**
     * Die vertikale Koordinate des Schachbretts (1-8)
     */
    @XmlEnum
    public enum Zeile implements EnumObject<Character> {
        _1, _2, _3, _4, _5, _6, _7, _8;

        @Override
        public Character marshal() {
            return name().charAt(1);
        }

        public static Zeile unmarshal(Character encoded) {
            return valueOf("_" + encoded);
        }

        public static Zeile[] valuesInverted() {
            return new Zeile[]{_8, _7, _6, _5, _4, _3, _2, _1};
        }

        @Override
        public String toString() {
            return "" + marshal();
        }
    }


    /**
     * Die Spalten-Koordinate des Schachbretts (a-h)
     */
    public final Spalte spalte;

    /**
     * Die Zeilen-Koordinate des Schachbretts (1-8)
     */
    public final Zeile zeile;


    /**
     * Konstruktor
     * @param spalte (horizontale) Spalten-Koordinate des Schachbretts (a-h)
     * @param zeile (vertikale) Zeilen-Koordinate des Schachbretts (1-8)
     */
    public Position(Spalte spalte, Zeile zeile) {
        this.spalte = spalte;
        this.zeile = zeile;
    }


    /**
     * Wird lediglich zum Unmarshallen der XML- bzw. Json-Objekte benoetigt
     */
    @SuppressWarnings("unused")
	private Position() {
        this(null, null);
    }


    @Override
    public String toString() {
        return spalte.toString() + zeile.toString();
    }

}
