package com.iks.dddschach.domain;

import com.iks.dddschach.olddomain.SpielNotationParser;

import java.text.ParseException;


public class HalbzugExt extends Halbzug {

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
        this(com.iks.dddschach.olddomain.Halbzug.fromOld(SpielNotationParser.parse(vonNach)));
    }

    public static HalbzugExt fromOld(com.iks.dddschach.olddomain.Halbzug halbzug) {
        return new HalbzugExt(com.iks.dddschach.olddomain.Position.fromOld(halbzug.from),
                com.iks.dddschach.olddomain.Position.fromOld(halbzug.to));
    }

}
