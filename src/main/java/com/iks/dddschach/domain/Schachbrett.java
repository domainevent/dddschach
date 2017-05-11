package com.iks.dddschach.domain;


import java.util.Arrays;

import static com.iks.dddschach.domain.Position.VertCoord._1;
import static com.iks.dddschach.domain.Position.VertCoord;
import static com.iks.dddschach.domain.Position.HorCoord;


/**
 * Beinhaltet die Stellung von Figuren auf dem Schachbrett
 */
public class Schachbrett extends ValueObject {

    protected final Spielfigur[][] board;

    protected Schachbrett(Spielfigur[][] board) {
        this.board = board;
    }


    /**
     * Default-Konstruktor
     */
    public Schachbrett() {
        this(new Spielfigur[HorCoord.values().length][VertCoord.values().length]);
    }

    /**
     * Kopier-Konstruktor
     * @param toCopy
     */
    public Schachbrett(Schachbrett toCopy) {
        this(toCopy.getBoard());
    }

    public Spielfigur[][] getBoard() {
        final Spielfigur[][] copy =
                new Spielfigur[HorCoord.values().length][VertCoord.values().length];
        for (int i = 0; i < HorCoord.values().length; i++) {
            for (int j = 0; j < VertCoord.values().length; j++) {
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

        return Arrays.deepEquals(board, that.board);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + Arrays.deepHashCode(board);
        return result;
    }


    @Override
    public String toString() {
        final String horLine = "-------------------------";
        String boardAsStr = horLine + System.lineSeparator();
        for(Position.VertCoord vertCoord : Position.VertCoord.valuesInverted()) {
            boardAsStr += "|";
            for(Position.HorCoord horCoord : Position.HorCoord.values()) {
                final Spielfigur figure = board[horCoord.ordinal()][vertCoord.ordinal()];
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
