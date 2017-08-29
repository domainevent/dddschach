package com.iks.dddschach.domain;


public class Spielfeld$ extends Spielfeld {

    public Spielfeld$() {
    }

    public Spielfeld$(Position$ position, Spielfigur$ spielfigur) {
        super(position, spielfigur);
    }
}
