package com.iks.dddschach.domain;

import com.iks.dddschach.domain.base.ValueObject;

import java.text.ParseException;


/**
 * Ein Halbzug im Schach ist der Zug einer Figur, beispielsweise des Bauern von e2 nach e4.
 * Erst die Kombination mit einem Gegenzug wird im Schach als <i>Zug</i> bezeichnet.
 */
public class Halbzug extends ValueObject {

    /**
     * Startposition des Halbzuges
     */
    public final Position from;

    /**
     * Zielposition des Halbzuges
     */
    public final Position to;


    /**
     * Konstruktor
     * @param from Startposition
     * @param to Zielposition
     */
    public Halbzug(Position from, Position to) {
        this.from = from;
        this.to = to;
    }


    /**
     * Konstruktor
     * @param from Startposition, Beispiel: "e2"
     * @param to Zielposition, Beispiel: "e4"
     * @throws ParseException falls <code>from</code> oder <code>to</code> nicht der Notationssyntax entsprechen
     */
    public Halbzug(String from, String to) throws ParseException {
        this(new Position(from), new Position(to));
    }


    public Halbzug(String fromTo) throws ParseException {
        String[] fromToParts;
        try {
            fromToParts = fromTo.split("-");
            this.from = new Position(fromToParts[0]);
            this.to = new Position(fromToParts[1]);
        }
        catch (Exception e) {
            throw new ParseException(fromTo, 0);
        }
    }

    /**
     * Wird lediglich zum Unmarshallen der XML- bzw. Json-Objekte benoetigt
     */
    private Halbzug() {
        this((Position)null, (Position)null);
    }


    @Override
    public String toString() {
        return from + "-" + to;
    }
}
