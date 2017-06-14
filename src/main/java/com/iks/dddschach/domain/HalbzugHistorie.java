package com.iks.dddschach.domain;

import com.iks.dddschach.domain.base.EntityObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by vollmer on 18.05.17.
 */
public class HalbzugHistorie extends EntityObject {

    protected List<Halbzug> halbzuege = new ArrayList<>();

    public void addHalbzug(Halbzug halbzug) {
        halbzuege.add(halbzug);
    }

    public List<Halbzug> getHalbzuege() {
        return halbzuege;
    }

    public int size() {
        return halbzuege.size();
    }

}
