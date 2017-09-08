package com.javacook.dddschach.domain;

import com.javacook.dddschach.domain.base.ValueObject;

import java.util.List;
import java.util.stream.Collectors;

public class HalbzugHistorie extends HalbzugHistorie0 implements ValueObject {

    public HalbzugHistorie() {
    }

    public HalbzugHistorie(List<Halbzug0> halbzuege) {
        super(halbzuege);
    }


    public List<Halbzug> getHalbzuege$() {
        return super.getHalbzuege().stream().map(h -> (Halbzug)h).collect(Collectors.toList());
    }
}