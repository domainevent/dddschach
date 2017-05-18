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
    public enum Zeile implements EnumObject<Zeile,Character> {
        A, B, C, D, E, F, G, H;

        @Override
        public Character marshal() {
            return name().toLowerCase().charAt(0);
        }

        public static Zeile unmarshal(Character encoded) {
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
    public enum Spalte implements EnumObject<Spalte,Character> {
        _1, _2, _3, _4, _5, _6, _7, _8;

        @Override
        public Character marshal() {
            return name().charAt(1);
        }

        public static Spalte unmarshal(Character encoded) {
            return valueOf("_" + encoded);
        }

        public static Spalte[] valuesInverted() {
            return new Spalte[]{_8, _7, _6, _5, _4, _3, _2, _1};
        }

        @Override
        public String toString() {
            return "" + marshal();
        }
    }


    /**
     * Die (horizontale) Zeilen-Koordinate des Schachbretts (a-h)
     */
    public final Zeile horCoord;

    /**
     * Die (vertikale) Spalten-Koordinate des Schachbretts (1-8)
     */
    public final Spalte vertCoord;


    public Position(Zeile zeile, Spalte spalte) {
        this.horCoord = zeile;
        this.vertCoord = spalte;
    }

    public Position(String coordEncoded) {
        horCoord = Zeile.valueOf(coordEncoded.substring(0,1).toUpperCase());
        vertCoord = Spalte.valueOf("_" + coordEncoded.substring(1,2));
    }

    /**
     * Wird zum Unmarshallen der XML- bzw. Json-Objekte benoetigt
     */
    private Position() {
        this(null, null);
    }


    @Override
    public String toString() {
        return horCoord.toString() + vertCoord.toString();
    }

}
