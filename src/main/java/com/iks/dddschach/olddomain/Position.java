package com.iks.dddschach.olddomain;

import com.iks.dddschach.domain.base.EnumObject;
import com.iks.dddschach.domain.base.ValueObject;
import javafx.geometry.Pos;

import javax.validation.constraints.NotNull;
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

        public static Spalte fromNew(com.iks.dddschach.domain.Spalte spalte) {
            return Spalte.values()[spalte.ordinal()];
        }

        public static com.iks.dddschach.domain.Spalte fromOld(Spalte spalte) {
            return com.iks.dddschach.domain.Spalte.values()[spalte.ordinal()];
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

        public static Zeile fromNew(com.iks.dddschach.domain.Zeile zeile) {
            return Zeile.values()[zeile.ordinal()];
        }

        public static com.iks.dddschach.domain.Zeile fromOld(Zeile spalte) {
            return com.iks.dddschach.domain.Zeile.values()[spalte.ordinal()];
        }
    }


    /**
     * Die (horizontale) Zeilen-Koordinate des Schachbretts (a-h)
     */
    @NotNull
    public final Spalte horCoord;

    /**
     * Die (vertikale) Spalten-Koordinate des Schachbretts (1-8)
     */
    @NotNull
    public final Zeile vertCoord;


    /**
     * Konstruktor
     * @param spalte (vertikale) Spalten-Koordinate des Schachbretts (1-8)
     * @param zeile (horizontale) Zeilen-Koordinate des Schachbretts (a-h)
     */
    public Position(Spalte spalte, Zeile zeile) {
        this.horCoord = spalte;
        this.vertCoord = zeile;
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
        return "" +
                (horCoord  == null? "<null>" : horCoord) +
                (vertCoord  == null? "<null>" : vertCoord);
    }


    public static Position fromNew(com.iks.dddschach.domain.Position position) {
        return new Position(Spalte.fromNew(position.getSpalte()), Zeile.fromNew(position.getZeile()));
    }

    public static com.iks.dddschach.domain.Position fromOld(Position position) {
        return new com.iks.dddschach.domain.Position(Zeile.fromOld(position.vertCoord), Spalte.fromOld(position.horCoord));
    }

}
