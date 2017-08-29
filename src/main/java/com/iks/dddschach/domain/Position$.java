package com.iks.dddschach.domain;

import com.iks.dddschach.domain.base.ValueObject;


public class Position$ extends Position implements ValueObject {

    public Position$() {
    }

    public Position$(Spalte spalte, Zeile zeile) {
        super(spalte, zeile);
    }
}
