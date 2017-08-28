package com.iks.dddschach.domain.validation;

import com.iks.dddschach.domain.*;
import com.iks.dddschach.util.IntegerTupel;

import java.util.Objects;


/**
 * @author javacook
 */
public class ValidationUtils {

    public static IntegerTupel toIntegerTupel(Position pos) {
        return new IntegerTupel(pos.getZeile().ordinal(), pos.getSpalte().ordinal());
    }

    public static Position toPosition(IntegerTupel tupel) {
        return new Position(Zeile.values()[tupel.y()], Spalte.values()[tupel.x()]);
    }

    public static Position middle(Position from, Position to) {
        final IntegerTupel middel = IntegerTupel.middel(toIntegerTupel(from), toIntegerTupel(to));
        return toPosition(middel);
    }

    public static Farbe spielerFarbe(int zugnummer) {
        return zugnummer % 2 == 0? Farbe.WEISS : Farbe.SCHWARZ;
    }

    /**
     * Ermittelt die Farbe des Spielers, der den Halbzug <code>halbzug</code> ausführen will
     */
    public static Farbe spielerFarbe(Halbzug halbzug, SpielbrettExt spielbrett) {
        final Spielfigur zugFigur = spielbrett.getSchachfigurAnPosition(halbzug.getVon());
        Objects.requireNonNull(zugFigur, "There is no figure on " + halbzug.getVon());
        return zugFigur.getFarbe();
    }

}
