package com.javacook.dddschach.service.api;

import com.javacook.dddschach.domain.FuehreHalbzugAusRequest0;
import com.javacook.dddschach.domain.Halbzug;
import com.javacook.dddschach.domain.SpielId;


public class FuehreHalbzugAusRequest extends FuehreHalbzugAusRequest0 {

    public FuehreHalbzugAusRequest(SpielId spielId, Halbzug halbzug) {
        super(spielId, halbzug);
    }

    public FuehreHalbzugAusRequest() {
    }


    @Override
    public Halbzug getHalbzug() {
        return new Halbzug(halbzug);
    }

    @Override
    public SpielId getSpielId() {
        return new SpielId(spielId);
    }
}
