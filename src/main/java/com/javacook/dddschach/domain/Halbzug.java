package com.javacook.dddschach.domain;

import com.javacook.dddschach.domain.base.ValueObject;

import java.text.ParseException;

public class Halbzug extends Halbzug0 implements ValueObject {

    public Halbzug() {
        super();
    }

    public Halbzug(Position von, Position nach) {
        super(von, nach);
    }

    public Halbzug(Halbzug0 halbzug) {
        this(new Position(halbzug.von), new Position(halbzug.nach));
    }

    public Halbzug(String vonNach) throws ParseException {
        this(SpielNotationParser.parse(vonNach));
    }

    @Override
    public Position getVon() {
        return (Position)super.getVon();
    }

    @Override
    public Position getNach() {
        return (Position)super.getNach();
    }


    public String encode() {
        return getVon().encode() + "-" + getNach().encode();
    }

}