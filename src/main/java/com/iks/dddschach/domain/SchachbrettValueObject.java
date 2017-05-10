package com.iks.dddschach.domain;


import java.util.Arrays;

import static com.iks.dddschach.domain.PositionValueObject.VertCoord._1;
import static com.iks.dddschach.domain.PositionValueObject.VertCoord;
import static com.iks.dddschach.domain.PositionValueObject.HorCoord;


/**
 * Created by vollmer on 05.05.17.
 */
public class SchachbrettValueObject extends ValueObject {

    protected final SpielfigurValueObject[][] board;

    protected SchachbrettValueObject(SpielfigurValueObject[][] board) {
        this.board = board;
    }


    /**
     * Default-Konstruktor
     */
    public SchachbrettValueObject() {
        this(new SpielfigurValueObject[HorCoord.values().length][VertCoord.values().length]);
    }

    /**
     * Kopier-Konstruktor
     * @param toCopy
     */
    public SchachbrettValueObject(SchachbrettValueObject toCopy) {
        this(toCopy.getBoard());
    }

    public SpielfigurValueObject[][] getBoard() {
        final SpielfigurValueObject[][] copy =
                new SpielfigurValueObject[HorCoord.values().length][VertCoord.values().length];
        for (int i = 0; i < HorCoord.values().length; i++) {
            for (int j = 0; j < VertCoord.values().length; j++) {
                copy[i][j] = board[i][j];
            }
        }
        return copy;
    }

    protected void setSchachfigurAnPosition(PositionValueObject position, SpielfigurValueObject figur) {
        board[position.horCoord.ordinal()][position.vertCoord.ordinal()] = figur;
    }

    protected SpielfigurValueObject getSchachfigurAnPosition(PositionValueObject position) {
        return board[position.horCoord.ordinal()][position.vertCoord.ordinal()];
    }

    protected void setSchachfigurAnPosition(HorCoord h, VertCoord v,
            SpielfigurValueObject.FigureEnum figur, FarbeEnum color) {
        setSchachfigurAnPosition(new PositionValueObject(h,v), new SpielfigurValueObject(figur, color));
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        SchachbrettValueObject that = (SchachbrettValueObject) o;

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
        for(PositionValueObject.VertCoord vertCoord : PositionValueObject.VertCoord.valuesInverted()) {
            boardAsStr += "|";
            for(PositionValueObject.HorCoord horCoord : PositionValueObject.HorCoord.values()) {
                final SpielfigurValueObject figure = board[horCoord.ordinal()][vertCoord.ordinal()];
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
