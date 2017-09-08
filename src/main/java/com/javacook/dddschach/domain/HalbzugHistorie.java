package com.javacook.dddschach.domain;

import com.javacook.dddschach.domain.base.ValueObject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HalbzugHistorie extends HalbzugHistorie0 implements ValueObject {

    public HalbzugHistorie() {
    }

    public HalbzugHistorie(List<? extends Halbzug0> halbzuege) {
        super(new ArrayList<>(halbzuege));
    }

    public List<Halbzug> getHalbzugList() {
        return super.getHalbzuege().stream().map(h -> (Halbzug)h).collect(Collectors.toList());
    }
}
