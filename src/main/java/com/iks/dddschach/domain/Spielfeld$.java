package com.iks.dddschach.domain;

public class Spielfeld$ extends Spielfeld {

    public Spielfeld$() {
    }

    public Spielfeld$(Position$ position, Spielfigur$ spielfigur) {
        super(position, spielfigur);
    }

    public Spielfeld$(Position position, Spielfigur spielfigur) {
        this(new Position$(position), new Spielfigur$(spielfigur));
    }

    @Override
    public Spielfigur$ getSpielfigur() {
        return (Spielfigur$)spielfigur;
    }

    @Override
    public Position$ getPosition() {
        return (Position$)position;
    }

}
