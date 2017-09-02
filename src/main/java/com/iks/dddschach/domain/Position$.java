package com.iks.dddschach.domain;

import com.iks.dddschach.domain.base.ValueObject;

public class Position$ extends Position implements ValueObject {

    public Position$() {
    }

    public Position$(Position position) {
        this(position.spalte, position.zeile);
    }

    public Position$(Spalte spalte, Zeile zeile) {
        super(spalte, zeile);
    }

    public String asString() {
        return getSpalte().value().toLowerCase() + asString(getZeile());
    }

    public static String asString(Zeile z) {
        switch (z) {
            case I: return "1";
            case II: return "2";
            case III: return "3";
            case IV: return "4";
            case V: return "5";
            case VI: return "6";
            case VII: return "7";
            case VIII: return "8";
            default: throw new IllegalArgumentException("" + z);
        }
    }
}
