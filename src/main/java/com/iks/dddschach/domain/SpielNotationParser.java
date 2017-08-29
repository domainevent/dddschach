package com.iks.dddschach.domain;

import com.iks.dddschach.domain.*;

import java.text.ParseException;


/**
 * Dient dazu, eine Eingabe in vereinfachter Notation, z.B. "e2-e4" zu parsen.
 */
public class SpielNotationParser {

    /**
     * Analysiert Halbzugeingabe der Form [a-h][1-8]-[a-h][1-8]
     * @param eingabe Die textuelle Eingabe eines Halbzugs
     * @return {@link Halbzug}
     * @throws ParseException falls <code>eingabe</code> nicht der Syntax entsprach
     */
    public static HalbzugExt parse(String eingabe) throws ParseException {
        String[] fromToParts;
        try {
            fromToParts = eingabe.split("-");
            Position from = parsePosition(fromToParts[0]);
            Position to = parsePosition(fromToParts[1]);
            return new HalbzugExt(from, to);
        }
        catch (Exception e) {
            throw new ParseException(eingabe, 0);
        }
    }

    /**
     * Analysiert Positionseingaben der Form [a-h][1-8]
     * @param eingabe Die textuelle Eingabe der Position Halbzugs
     * @return {@link Position}
     * @throws ParseException falls <code>eingabe</code> nicht der Syntax entsprach
     */
    public static Position parsePosition(String eingabe) throws ParseException {
        try {
            Spalte spalte = Spalte.valueOf(eingabe.substring(0, 1).toUpperCase());
            Zeile zeile = parseZeile(eingabe.substring(1, 2));
            return new Position(spalte, zeile);
        }
        catch (Exception e) {
            throw new ParseException(eingabe, 0);
        }
    }

    private static Zeile parseZeile(String zeile) {
        switch (zeile) {
            case "1": return Zeile.I;
            case "2": return Zeile.II;
            case "3": return Zeile.III;
            case "4": return Zeile.IV;
            case "5": return Zeile.V;
            case "6": return Zeile.VI;
            case "7": return Zeile.VII;
            case "8": return Zeile.VIII;
            default: throw new IllegalArgumentException("Invalid enum (for Zeile): " + zeile);
        }
    }

}
