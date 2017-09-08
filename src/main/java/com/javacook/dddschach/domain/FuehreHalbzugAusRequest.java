package com.javacook.dddschach.domain;

public class FuehreHalbzugAusRequest extends FuehreHalbzugAusRequest0 {

    public FuehreHalbzugAusRequest(SpielId spielId, Halbzug halbzug) {
        super(spielId, halbzug);
    }

    public FuehreHalbzugAusRequest() {
    }


    @Override
    public Halbzug getHalbzug() {
        return halbzug;
    }

    @Override
    public SpielId getSpielId() {
        return spielId;
    }
}
