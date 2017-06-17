package com.iks.dddschach.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iks.dddschach.domain.Spielfigur.FigurenTyp;
import com.iks.dddschach.domain.base.ValueObject;
import com.iks.dddschach.domain.validation.Zugregel;
import com.webcohesion.enunciate.metadata.DocumentationExample;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static com.iks.dddschach.domain.Position.Zeile._1;
import static com.iks.dddschach.domain.Position.Zeile;
import static com.iks.dddschach.domain.Position.Spalte;
import static com.iks.dddschach.domain.Spielfigur.FigurenTyp.KOENIG;


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
        board = new Spielfigur[Spalte.values().length][Zeile.values().length];
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
        final Spielfigur[][] copy = new Spielfigur[Spalte.values().length][Zeile.values().length];

        for (int i = 0; i < Spalte.values().length; i++) {
            for (int j = 0; j < Zeile.values().length; j++) {
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


    /**
     * Setzt eine Figur <code>figur</code> auf die Spielbrett-Position <code>position</code>
     * @param position Position auf dem Spielfeld (z.B. b4)
     * @param figur die zu setzende Figur (z.B. Lw = weißer Läufer)
     */
    protected void setSchachfigurAnPosition(Position position, Spielfigur figur) {
        board[position.horCoord.ordinal()][position.vertCoord.ordinal()] = figur;
    }


    /**
     * Setzt eine Figur, gegeben durch Typ und Farbe auf eine Spielbrett-Position gegeben durch Zeile und Spalte
     * @param h Zeile der Position auf dem Spielfeld (z.B. b)
     * @param v Spalte der Position auf dem Spielfeld (z.B. 5)
     * @param figurenTyp Typ der zu setzenden Figur (z.B. Läufer)
     * @param color Farbe der zu setzenden Figur (z.B. schwarz)
     */
    protected void setSchachfigurAnPosition(Spalte h, Zeile v, FigurenTyp figurenTyp, Farbe color) {
        setSchachfigurAnPosition(new Position(h,v), new Spielfigur(figurenTyp, color));
    }


    /**
     * Ermittelt die Spielfigur auf Position <code>position</code>
     * @param position Position auf dem Spielfeld (z.B. c3)
     * @return {@link Spielfigur} falls sich eine Figur auf Position <code>position</code> befindet, null sonst.
     */
    public Spielfigur getSchachfigurAnPosition(Position position) {
        return board[position.horCoord.ordinal()][position.vertCoord.ordinal()];
    }


    /**
     * Ermittelt alle Positionen des Spielfeldes, auf dem eine Spielfigur mit der Farbe
     * <code>farbe</code> steht.
     * @param farbe Farbe, dessen Positionen gesucht werden
     * @return Menge der Positionen, auf dem eine Figur mit Farbe <code>farbe</code> steht
     */
    public Set<Position> getPositionenMitFarbe(Farbe farbe) {
        return getAllePositionen().stream()
                .filter(position -> {
                    Spielfigur spielfigur = getSchachfigurAnPosition(position);
                    return spielfigur != null && spielfigur.color == farbe;
                })
                .collect(Collectors.toSet());
    }


    /**
     * Ermittelt alle Positionen des Spielfeldes
     * @return Alle Positionen des Spielfeldes
     */
    @JsonIgnore
    public Set<Position> getAllePositionen() {
        Set<Position> positionen = new HashSet<>();
        for (Spalte spalte : Spalte.values()) {
            for (Zeile zeile : Zeile.values()) {
                positionen.add(new Position(spalte, zeile));
            }
        }
        return positionen;
    }

    public Position sucheKoenigsPosition(Farbe farbeDesKoenigs) {
        for (Position lfdPos : getPositionenMitFarbe(farbeDesKoenigs)) {
            final Spielfigur spielfigur = getSchachfigurAnPosition(lfdPos);
            if (spielfigur != null && spielfigur.figure == KOENIG) {
                return lfdPos;
            }
        }
        throw new IllegalArgumentException("There is no " + farbeDesKoenigs + " king on the board");
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
        for(Zeile zeile : Zeile.valuesInverted()) {
            boardAsStr += "|";
            for(Spalte spalte : Spalte.values()) {
                final Spielfigur figure = board[spalte.ordinal()][zeile.ordinal()];
                boardAsStr += (figure == null)? "  " : figure;
                boardAsStr += "|";
            }
            boardAsStr += CR + horLine;
            if (zeile != _1) {
                boardAsStr += CR;
            }
        }
        return boardAsStr;
    }

}
