package com.iks.dddschach.domain.validation;

import com.iks.dddschach.domain.Position;
import com.iks.dddschach.util.IntegerTupel;


/**
 * @author javacook
 */
public class ValidationUtils {

    public static IntegerTupel toIntegerTupel(Position pos) {
        return new IntegerTupel(pos.horCoord.ordinal(), pos.vertCoord.ordinal());
    }

    public static Position toPosition(IntegerTupel tupel) {
        return new Position(Position.Spalte.values()[tupel.x()], Position.Zeile.values()[tupel.y()]);
    }

}
