package com.iks.dddschach.domain;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

import static com.iks.dddschach.domain.Farbe.WEISS;
import static java.lang.Character.toLowerCase;
import static java.lang.Character.toUpperCase;


public class SpielbrettExt extends Spielbrett {

    public SpielbrettExt() {
        super();
    }

    /**
     * Fully-initialising value constructor
     */
    public SpielbrettExt(final List<Spielfeld> spielfelder) {
        super(spielfelder);
    }


    public SpielbrettExt(final com.iks.dddschach.olddomain.Spielfigur[][] spielfiguren) {
        final int ANZAHL_SPALTEN = Spalte.values().length;
        final int ANZAHL_ZEILEN = Zeile.values().length;
        spielfelder = new ArrayList<>();
        for (int s = 0; s < ANZAHL_SPALTEN; s++) {
            for (int z = 0; z < ANZAHL_ZEILEN; z++) {
                com.iks.dddschach.olddomain.Spielfigur spielfigur = spielfiguren[s][z];
                if (spielfigur == null) continue;
                final FigurenTyp figurenTyp = FigurenTyp.valueOf(spielfigur.figure.name());
                final Farbe color = Farbe.valueOf(spielfigur.color.name());
                final Spielfigur spielfigur1 = new Spielfigur(figurenTyp, color);
                final Spielfeld spielfeld = new Spielfeld(
                        new Position(Zeile.values()[z], Spalte.values()[s]), spielfigur1);
                spielfelder.add(spielfeld);
            }
        }
    }


    public Spielfigur[][] getBoard() {
        final int ANZAHL_SPALTEN = Spalte.values().length;
        final int ANZAHL_ZEILEN = Zeile.values().length;
        final Spielfigur[][] copy = new Spielfigur[ANZAHL_SPALTEN][ANZAHL_ZEILEN];

        for (Spielfeld spielfigur : spielfelder) {
            final int z = spielfigur.getPosition().getZeile().ordinal();
            final int s = spielfigur.getPosition().getSpalte().ordinal();
            copy[s][z] = spielfigur.getSpielfigur();
        }
        return copy;
    }

    /**
     * Gibt die Spielposition in möglichst komprimierter Form aus (String der Länge 64)
     */
    public String encode() {
        String result = "";
        for (Zeile zeile : Zeile.values()) {
            for (Spalte spalte : Spalte.values()) {
                Spielfigur figure = getBoard()[spalte.ordinal()][zeile.ordinal()];
                if (figure == null) {
                    result += "_";
                }
                else {
                    char ch = encodeFigure(figure.getFigure());
                    result += (figure.getColor() == WEISS)? toUpperCase(ch) : toLowerCase(ch);
                }
            }
        }
        return result;
    }


    public Character encodeFigure(FigurenTyp figur) {
        switch (figur) {
            case KOENIG:  return 'K';
            case DAME:    return 'Q';
            case TURM:    return 'R';
            case LAEUFER: return 'B';
            case SPRINGER: return 'N';
            case BAUER:   return 'P';
        }
        throw new IllegalArgumentException("Unexpected enum " + this);
    }

}
