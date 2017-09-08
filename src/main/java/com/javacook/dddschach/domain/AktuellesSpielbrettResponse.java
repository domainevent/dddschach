package com.javacook.dddschach.domain;

public class AktuellesSpielbrettResponse extends AktuellesSpielbrettResponse0 {

    public AktuellesSpielbrettResponse(Spielbrett spielbrett) {
        super(spielbrett);
    }


    public AktuellesSpielbrettResponse() {
    }

    @Override
    public Spielbrett getSpielbrett() {
        return spielbrett;
    }
}
