package com.iks.dddschach.domain;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by vollmer on 18.05.17.
 */
public class HalbzugHistorie {

    List<Halbzug> halbzuege = new ArrayList<>();

    public void addHalbzug(Halbzug halbzug) {
        halbzuege.add(halbzug);
    }

    public int size() {
        return halbzuege.size();
    }

}
