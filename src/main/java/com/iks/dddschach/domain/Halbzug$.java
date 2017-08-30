package com.iks.dddschach.domain;

import com.iks.dddschach.domain.base.ValueObject;
import java.text.ParseException;


public class Halbzug$ extends Halbzug implements ValueObject {

    public Halbzug$() {
        super();
    }

    public Halbzug$(Position$ von, Position$ nach) {
        super(von, nach);
    }

    public Halbzug$(Halbzug halbzug) {
        this(halbzug.von, halbzug.nach);
    }


    public Halbzug$(String vonNach) throws ParseException {
        this(SpielNotationParser.parse(vonNach));
    }

    public Position$ getVon() {
        return (Position$)super.getVon();
    }

    public Position$ getNach() {
        return (Position$)super.getNach();
    }

}
