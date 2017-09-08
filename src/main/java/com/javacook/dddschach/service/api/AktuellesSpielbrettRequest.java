package com.javacook.dddschach.service.api;

import com.javacook.dddschach.domain.AktuellesSpielbrettRequest0;
import com.javacook.dddschach.domain.SpielId;


public class AktuellesSpielbrettRequest extends AktuellesSpielbrettRequest0 {

    public AktuellesSpielbrettRequest(String clientId, SpielId spielId) {
        super(clientId, spielId);
    }


    public AktuellesSpielbrettRequest() {
    }


    @Override
    public SpielId getSpielId() {
        return new SpielId(spielId);
    }

}