package com.iks.dddschach.domain.validation;

import com.iks.dddschach.domain.Position;
import com.iks.dddschach.util.IntegerTupel;

import static com.iks.dddschach.domain.validation.ValidationUtils.toIntegerTupel;


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

    public static Position middle(Position from, Position to) {
        final IntegerTupel middel = IntegerTupel.middel(toIntegerTupel(from), toIntegerTupel(to));
        return toPosition(middel);
    }

}
