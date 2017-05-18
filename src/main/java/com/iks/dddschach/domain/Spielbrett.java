package com.iks.dddschach.domain;


import com.iks.dddschach.domain.base.ValueObject;
import com.webcohesion.enunciate.metadata.DocumentationExample;

import java.util.Arrays;

import static com.iks.dddschach.domain.Position.Spalte._1;
import static com.iks.dddschach.domain.Position.Spalte;
import static com.iks.dddschach.domain.Position.Zeile;


/**
 * Beinhaltet die Stellung der Figuren auf dem Schachbrett
 */
public class Spielbrett extends ValueObject {

    protected final Spielfigur[][] board;


    /**
     * Default-Konstruktor
     */
    public Spielbrett() {
        this(new Spielfigur[Position.Zeile.values().length][Spalte.values().length]);
    }

    /**
     * Kopier-Konstruktor
     * @param toCopy das zu kopierenden {@link Spielbrett}
     */
    public Spielbrett(Spielbrett toCopy) {
        this(toCopy.getBoard());
    }

    /**
     * Assign-Konstruktor
     * @param board das zu übernehmenden zweidimensionale Array von Spielfiguren
     */
    protected Spielbrett(Spielfigur[][] board) {
        this.board = board;
    }

    /**
     * Ein zweidimensionales Array (real 8x8) von Spielfiguren
     * @see {@link Spielfigur}
     */
    @DocumentationExample(exclude = true)
    public Spielfigur[][] getBoard() {
        final Spielfigur[][] copy =
                new Spielfigur[Position.Zeile.values().length][Spalte.values().length];
        for (int i = 0; i < Position.Zeile.values().length; i++) {
            for (int j = 0; j < Spalte.values().length; j++) {
                copy[i][j] = board[i][j];
            }
        }
        return copy;
    }

    protected void setSchachfigurAnPosition(Position position, Spielfigur figur) {
        board[position.horCoord.ordinal()][position.vertCoord.ordinal()] = figur;
    }

    protected Spielfigur getSchachfigurAnPosition(Position position) {
        return board[position.horCoord.ordinal()][position.vertCoord.ordinal()];
    }

    protected void setSchachfigurAnPosition(Zeile h, Position.Spalte v,
                                            Spielfigur.FigurenTyp figur, Farbe color) {
        setSchachfigurAnPosition(new Position(h,v), new Spielfigur(figur, color));
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        try {
            Spielbrett that = (Spielbrett) o;
            return Arrays.deepEquals(board, that.board);
        }
        catch (ClassCastException e) {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(board);
    }

    public final static String CR = System.lineSeparator();

    /**
     * Gibt das Schachbrett in einer graphischen Art und Weise aus.
     */
    @Override
    public String toString() {
        final String horLine = "-------------------------";
        String boardAsStr = horLine + CR;
        for(Position.Spalte spalte : Position.Spalte.valuesInverted()) {
            boardAsStr += "|";
            for(Position.Zeile zeile : Position.Zeile.values()) {
                final Spielfigur figure = board[zeile.ordinal()][spalte.ordinal()];
                boardAsStr += (figure == null)? "  " : figure;
                boardAsStr += "|";
            }
            boardAsStr += CR + horLine;
            if (spalte != _1) {
                boardAsStr += CR;
            }
        }
        return boardAsStr;
    }

}
