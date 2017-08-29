package com.iks.dddschach.domain;

import com.iks.dddschach.domain.base.ValueObject;

import java.util.List;


public class HalbzugHistorie$ extends HalbzugHistorie implements ValueObject {

    public HalbzugHistorie$() {
    }


    public HalbzugHistorie$(List<Halbzug> halbzuege) {
        super(halbzuege);
    }
}
