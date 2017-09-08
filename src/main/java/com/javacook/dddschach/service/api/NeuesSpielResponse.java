package com.javacook.dddschach.service.api;

import com.javacook.dddschach.domain.NeuesSpielResponse0;
import com.javacook.dddschach.domain.SpielId;


public class NeuesSpielResponse extends NeuesSpielResponse0 {

    public NeuesSpielResponse(SpielId spielId) {
        super(spielId);
    }


    public NeuesSpielResponse() {
    }

    @Override
    public SpielId getSpielId() {
        return new SpielId(spielId);
    }
}
