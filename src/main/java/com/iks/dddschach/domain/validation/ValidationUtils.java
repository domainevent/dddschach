package com.iks.dddschach.domain.validation;

import com.iks.dddschach.domain.Position;
import com.iks.dddschach.util.IntegerTupel;


/**
 * Created by vollmer on 02.06.17.
 */
public class ValidationUtils {

    public static IntegerTupel toIntegerTupel(Position pos) {
        return new IntegerTupel(pos.horCoord.ordinal(), pos.vertCoord.ordinal());
    }

    public static Position toPosition(IntegerTupel tupel) {
        return new Position(Position.Zeile.values()[tupel.x()], Position.Spalte.values()[tupel.y()]);
    }

}
