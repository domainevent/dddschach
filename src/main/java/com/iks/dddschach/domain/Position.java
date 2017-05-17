package com.iks.dddschach.domain;


import javax.xml.bind.annotation.XmlEnum;


/**
 * Repräsentiert eine Schachbrett-Koordinate (z.B. b4)
 */
public class Position extends ValueObject {

    /**
     * Die horizontale Koordinate des Schachbretts (a-h)
     */
    @XmlEnum
    public enum HorCoord {
        A, B, C, D, E, F, G, H;

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }

    /**
     * Die vertikale Koordinate des Schachbretts (1-8)
     */
    @XmlEnum
    public enum VertCoord {
        _1, _2, _3, _4, _5, _6, _7, _8;

        public static VertCoord[] valuesInverted() {
            return new VertCoord[]{_8, _7, _6, _5, _4, _3, _2, _1};
        }

        @Override
        public String toString() {
            return String.valueOf(ordinal()+1);
        }
    }


    /**
     * Die horizontale Koordinate des Schachbretts (a-h)
     */
    public final HorCoord horCoord;

    /**
     * Die vertikale Koordinate des Schachbretts (1-8)
     */
    public final VertCoord vertCoord;

    public Position(HorCoord horCoord, VertCoord vertCoord) {
        this.horCoord = horCoord;
        this.vertCoord = vertCoord;
    }


    public Position() {
        this(null, null);
    }


    public Position(String coordEncoded) {
        horCoord = HorCoord.valueOf(coordEncoded.substring(0,1).toUpperCase());
        vertCoord = VertCoord.valueOf("_" + coordEncoded.substring(1,2));
    }


    @Override
    public String toString() {
        return horCoord.toString() + vertCoord.toString();
    }

}
