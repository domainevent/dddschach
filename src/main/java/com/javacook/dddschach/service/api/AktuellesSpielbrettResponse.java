package com.javacook.dddschach.service.api;

import com.javacook.dddschach.domain.AktuellesSpielbrettResponse0;
import com.javacook.dddschach.domain.Spielbrett;


public class AktuellesSpielbrettResponse extends AktuellesSpielbrettResponse0 {

    public AktuellesSpielbrettResponse(Spielbrett spielbrett) {
        super(spielbrett);
    }


    public AktuellesSpielbrettResponse() {
    }

    @Override
    public Spielbrett getSpielbrett() {
        return new Spielbrett(spielbrett);
    }
}
