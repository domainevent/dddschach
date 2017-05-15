package com.iks.dddschach.domain;


import com.webcohesion.enunciate.metadata.DocumentationExample;

import javax.xml.bind.annotation.XmlType;
import java.util.Arrays;

import static com.iks.dddschach.domain.Position.VertCoord._1;
import static com.iks.dddschach.domain.Position.VertCoord;
import static com.iks.dddschach.domain.Position.HorCoord;


/**
 * Beinhaltet die Stellung der Figuren auf dem Schachbrett
 */
@XmlType
public class Schachbrett extends ValueObject {

    protected final Spielfigur[][] brett;


    /**
     * Default-Konstruktor
     */
    public Schachbrett() {
        this(new Spielfigur[HorCoord.values().length][VertCoord.values().length]);
    }

    /**
     * Kopier-Konstruktor
     * @param toCopy das zu kopierenden {@link Schachbrett}
     */
    public Schachbrett(Schachbrett toCopy) {
        this(toCopy.getBrett());
    }

    /**
     * Assign-Konstruktor
     * @param brett das zu übernehmenden zweidimensionale Array von Spielfiguren
     */
    protected Schachbrett(Spielfigur[][] brett) {
        this.brett = brett;
    }

    /**
     * Ein zweidimensionales Array (real 8x8) von Spielfiguren
     * @see {@link Spielfigur}
     */
    @DocumentationExample(exclude = true)
    public Spielfigur[][] getBrett() {
        final Spielfigur[][] copy =
                new Spielfigur[HorCoord.values().length][VertCoord.values().length];
        for (int i = 0; i < HorCoord.values().length; i++) {
            for (int j = 0; j < VertCoord.values().length; j++) {
                copy[i][j] = brett[i][j];
            }
        }
        return copy;
    }

    protected void setSchachfigurAnPosition(Position position, Spielfigur figur) {
        brett[position.horCoord.ordinal()][position.vertCoord.ordinal()] = figur;
    }

    protected Spielfigur getSchachfigurAnPosition(Position position) {
        return brett[position.horCoord.ordinal()][position.vertCoord.ordinal()];
    }

    protected void setSchachfigurAnPosition(HorCoord h, VertCoord v,
                                            Spielfigur.FigureEnum figur, FarbeEnum color) {
        setSchachfigurAnPosition(new Position(h,v), new Spielfigur(figur, color));
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Schachbrett that = (Schachbrett) o;

        return Arrays.deepEquals(brett, that.brett);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + Arrays.deepHashCode(brett);
        return result;
    }


    @Override
    public String toString() {
        final String horLine = "-------------------------";
        String boardAsStr = horLine + System.lineSeparator();
        for(Position.VertCoord vertCoord : Position.VertCoord.valuesInverted()) {
            boardAsStr += "|";
            for(Position.HorCoord horCoord : Position.HorCoord.values()) {
                final Spielfigur figure = brett[horCoord.ordinal()][vertCoord.ordinal()];
                boardAsStr += figure == null? "  " : figure.abbreviation();
                boardAsStr += "|";
            }
            boardAsStr += System.lineSeparator() + horLine;
            if (vertCoord != _1) {
                boardAsStr += System.lineSeparator();
            }
        }
        return boardAsStr;
    }

}
