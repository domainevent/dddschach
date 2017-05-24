package com.iks.dddschach.domain;


import com.iks.dddschach.domain.Spielfigur.FigurenTyp;
import com.iks.dddschach.domain.base.ValueObject;
import com.webcohesion.enunciate.metadata.DocumentationExample;

import java.util.Arrays;

import static com.iks.dddschach.domain.Position.Spalte._1;
import static com.iks.dddschach.domain.Position.Spalte;
import static com.iks.dddschach.domain.Position.Zeile;


/**
 * Beinhaltet die Stellung der Figuren auf dem Schachbrett.
 *
 * Hinweis: Beachte die Methode <code>wendeHalbzugAn</code>,
 * mit der auf dem Schachbrett ein Zug ausgeführt werden kann.
 */
public class Spielbrett extends ValueObject {

    /**
     * Zweidimensionales Array, das das Spielbrett repräsentiert.
     */
    protected final Spielfigur[][] board;

    /**
     * Default-Konstruktor
     */
    public Spielbrett() {
        board = new Spielfigur[Zeile.values().length][Spalte.values().length];
    }

    /**
     * Kopier-Konstruktor
     * @param toCopy das zu kopierenden {@link Spielbrett}
     */
    public Spielbrett(Spielbrett toCopy) {
        board = toCopy.getBoard();
    }


    /**
     * Ein zweidimensionales Array (real 8x8) von Spielfiguren
     * @return eine neue Instanz des Spielfiguren-Arrays (immutable)
     * @see {@link Spielfigur}
     */
    @DocumentationExample(exclude = true)
    public Spielfigur[][] getBoard() {
        final Spielfigur[][] copy = new Spielfigur[Zeile.values().length][Spalte.values().length];

        for (int i = 0; i < Zeile.values().length; i++) {
            for (int j = 0; j < Spalte.values().length; j++) {
                copy[i][j] = board[i][j];
            }
        }
        return copy;
    }


    /**
     * Wendet auf dem Spielbrett einen Halbzug an und gibt das Ergebnis zurück
     * @param halbzug der auf dem Brett anzuwendende Halbzug
     * @return eine neue Instanz des modifizierten Spielbretts
     * @see {@link Halbzug}
     */
    public Spielbrett wendeHalbzugAn(Halbzug halbzug) {
        return new Spielbrett(this) {{
            final Spielfigur spielfigurFrom = getSchachfigurAnPosition(halbzug.from);
            setSchachfigurAnPosition(halbzug.from, null);
            setSchachfigurAnPosition(halbzug.to, spielfigurFrom);
        }};
    }


    protected void setSchachfigurAnPosition(Position position, Spielfigur figur) {
        board[position.horCoord.ordinal()][position.vertCoord.ordinal()] = figur;
    }

    protected Spielfigur getSchachfigurAnPosition(Position position) {
        return board[position.horCoord.ordinal()][position.vertCoord.ordinal()];
    }

    protected void setSchachfigurAnPosition(Zeile h, Spalte v, FigurenTyp figur, Farbe color) {
        setSchachfigurAnPosition(new Position(h,v), new Spielfigur(figur, color));
    }


    // Überschriebende Standard-Methoden:

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
        for(Spalte spalte : Spalte.valuesInverted()) {
            boardAsStr += "|";
            for(Zeile zeile : Zeile.values()) {
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
