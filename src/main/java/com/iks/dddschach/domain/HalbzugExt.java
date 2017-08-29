package com.iks.dddschach.domain;

import com.iks.dddschach.domain.base.ValueObject;
import java.text.ParseException;


public class HalbzugExt extends Halbzug implements ValueObject {

    public HalbzugExt() {
        super();
    }

    public HalbzugExt(Position von, Position nach) {
        super(von, nach);
    }

    public HalbzugExt(Halbzug halbzug) {
        this(halbzug.von, halbzug.nach);
    }


    public HalbzugExt(String vonNach) throws ParseException {
        this(SpielNotationParser.parse(vonNach));
    }

}
