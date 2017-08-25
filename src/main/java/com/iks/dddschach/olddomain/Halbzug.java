package com.iks.dddschach.olddomain;

import com.iks.dddschach.domain.base.ValueObject;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;


/**
 * Ein Halbzug im Schach ist der Zug einer Figur, beispielsweise des Bauern von e2 nach e4.
 * Erst die Kombination mit einem Gegenzug wird im Schach als <i>Zug</i> bezeichnet.
 */
public class Halbzug extends ValueObject {

    /**
     * Startposition des Halbzuges
     */
    @Valid @NotNull
    public final Position from;

    /**
     * Zielposition des Halbzuges
     */
    @Valid @NotNull
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
     * Wird lediglich zum Unmarshallen der XML- bzw. Json-Objekte benoetigt
     */
    @SuppressWarnings("unused")
	private Halbzug() {
        this(null, null);
    }

    @Override
    public String toString() {
        return (from == null? "<null>" : from) + "-" + (to == null? "<null>" : to);
    }

}
