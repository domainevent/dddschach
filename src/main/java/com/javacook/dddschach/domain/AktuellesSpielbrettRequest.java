package com.javacook.dddschach.domain;

public class AktuellesSpielbrettRequest extends AktuellesSpielbrettRequest0 {

    public AktuellesSpielbrettRequest(String clientId, SpielId spielId) {
        super(clientId, spielId);
    }


    public AktuellesSpielbrettRequest() {
    }


    @Override
    public SpielId getSpielId() {
        return spielId;
    }

}