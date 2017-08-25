package com.iks.dddschach.domain;

import com.iks.dddschach.domain2.Spalte;
import com.iks.dddschach.domain2.Spielbrett;
import com.iks.dddschach.domain2.Spielfeld;
import com.iks.dddschach.domain2.Zeile;
import com.iks.dddschach.domain2.Spielfigur;

import static java.lang.Character.toLowerCase;
import static java.lang.Character.toUpperCase;


public class SpielbrettExt extends Spielbrett {

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
                    char ch = figure.figure.marshal();
                    result += (figure.color == Farbe.WEISS)? toUpperCase(ch) : toLowerCase(ch);
                }
            }
        }
        return result;
    }

}
