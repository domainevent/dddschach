package com.javacook.dddschach.domain;

public class NeuesSpielResponse extends NeuesSpielResponse0 {

    public NeuesSpielResponse(SpielId spielId) {
        super(spielId);
    }


    public NeuesSpielResponse() {
    }

    @Override
    public SpielId getSpielId() {
        return spielId;
    }
}
