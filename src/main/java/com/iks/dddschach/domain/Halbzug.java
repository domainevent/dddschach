package com.iks.dddschach.domain;

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
    public final Position von;

    /**
     * Zielposition des Halbzuges
     */
    @Valid @NotNull
    public final Position nach;


    /**
     * Konstruktor
     * @param von Startposition
     * @param nach Zielposition
     */
    public Halbzug(Position von, Position nach) {
        this.von = von;
        this.nach = nach;
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
        return (von == null? "<null>" : von) + "-" + (nach == null? "<null>" : nach);
    }
}
