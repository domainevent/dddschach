package com.iks.dddschach.domain;

import javax.xml.bind.annotation.XmlType;


/**
 * Ein Halbzug im Schach ist der Zug einer Figur, beispielsweise des Bauern von e2 nach e4.
 * Erst die Kombination mit einem Gegenzug wird im Schach als <i>Zug</i> bezeichnet.
 */
@XmlType
public class Halbzug extends ValueObject {

    public final Position from;
    public final Position to;


    public Halbzug() {
        this((Position)null, (Position)null);
    }


    public Halbzug(Position from, Position to) {
        this.from = from;
        this.to = to;
    }

    public Halbzug(String from, String to) {
        this(new Position(from), new Position(to));
    }

    public Halbzug(String fromTo) {
        this(fromTo.split("-")[0], fromTo.split("-")[1]);
    }

    @Override
    public String toString() {
        return from + "-" + to;
    }
}
