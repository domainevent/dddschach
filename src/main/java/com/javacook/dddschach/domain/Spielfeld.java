package com.javacook.dddschach.domain;

public class Spielfeld extends Spielfeld0 {

    public Spielfeld() {
    }

    public Spielfeld(Spielfeld0 spielfeld) {
        super(spielfeld.position, spielfeld.spielfigur);
    }

    public Spielfeld(Position position, Spielfigur spielfigur) {
        super(position, spielfigur);
    }

    public Spielfeld(Position position, Spielfigur0 spielfigur) {
        this(new Position(position), new Spielfigur(spielfigur));
    }

    @Override
    public Spielfigur getSpielfigur() {
        return new Spielfigur(spielfigur);
    }

    @Override
    public Position getPosition() {
        return new Position(position);
    }

}
