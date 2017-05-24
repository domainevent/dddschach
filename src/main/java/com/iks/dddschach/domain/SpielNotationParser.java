package com.iks.dddschach.domain;

import java.text.ParseException;


/**
 * Dient dazu, eine Eingabe in vereinfachter Notation, z.B. "e2-e4" zu parsen.
 */
public class SpielNotationParser {

    public static Halbzug parse(String eingabe) throws ParseException {
        String[] fromToParts;
        try {
            fromToParts = eingabe.split("-");
            Position from = parsePosition(fromToParts[0]);
            Position to = parsePosition(fromToParts[1]);
            return new Halbzug(from, to);
        }
        catch (Exception e) {
            throw new ParseException(eingabe, 0);
        }
    }


    public static Position parsePosition(String eingabe) throws ParseException {
        try {
            Position.Zeile zeile = Position.Zeile.valueOf(eingabe.substring(0, 1).toUpperCase());
            Position.Spalte spalte = Position.Spalte.valueOf("_" + eingabe.substring(1, 2));
            return new Position(zeile, spalte);
        }
        catch (Exception e) {
            throw new ParseException(eingabe, 0);
        }
    }

}
