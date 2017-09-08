package com.javacook.dddschach.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.javacook.dddschach.domain.base.ValueObject;
import com.javacook.dddschach.util.Misc;
import com.webcohesion.enunciate.metadata.DocumentationExample;

import java.util.*;
import java.util.stream.Collectors;

import static com.javacook.dddschach.domain.FigurenTyp.KOENIG;
import static com.javacook.dddschach.domain.Zeile.I;


public class Spielbrett extends Spielbrett0 implements ValueObject {

    public final static String CR = System.lineSeparator();
    public Spielbrett() {
        super(new ArrayList<>());
    }

    public List<Spielfeld> getSpielfelder$() {
        return spielfelder.stream().map(s -> (Spielfeld)s).collect(Collectors.toList());
    }

    /**
     * Kopier-Konstruktor
     * @param toCopy das zu kopierenden {@link Spielbrett}
     */
    public Spielbrett(Spielbrett0 toCopy) {
        this.spielfelder = new ArrayList<>(toCopy.spielfelder);
    }

    /*-----------------------------------------------------------------------*\
     * Blutige Ergänzungen                                                   *
    \*-----------------------------------------------------------------------*/

    /**
     * Gibt das Schachbrett in einer graphischen Art und Weise aus.
     */
    @Override
    public String toString() {
        final String horLine = "-------------------------";
        String boardAsStr = horLine + CR;
        final Spielfigur[][] boardAsArray = asArray();
        for(Zeile zeile : Misc.invertArray(Zeile.values())) {
            boardAsStr += "|";
            for(Spalte spalte : Spalte.values()) {
                final Spielfigur figure = boardAsArray[spalte.ordinal()][zeile.ordinal()];
                boardAsStr += (figure == null)? "  " : figure;
                boardAsStr += "|";
            }
            boardAsStr += CR + horLine;
            if (zeile != I) {
                boardAsStr += CR;
            }
        }
        return boardAsStr;
    }


    /**
     * Ein zweidimensionales Array (real 8x8) von Spielfiguren
     * @return eine neue Instanz des Spielfiguren-Arrays (immutable)
     * @see {@link Spielfigur}
     */
    @DocumentationExample(exclude = true)
    public Spielfigur[][] asArray() {
        final int ANZAHL_SPALTEN = Spalte.values().length;
        final int ANZAHL_ZEILEN = Zeile.values().length;
        final Spielfigur[][] copy = new Spielfigur[ANZAHL_SPALTEN][ANZAHL_ZEILEN];

        for (Spielfeld spielfeld : getSpielfelder$()) {
            final int z = spielfeld.getPosition().getZeile().ordinal();
            final int s = spielfeld.getPosition().getSpalte().ordinal();
            copy[s][z] = spielfeld.getSpielfigur();
        }
        return copy;
    }

    /**
     * Gibt das Spielbrett in möglichst komprimierter Form aus (String der Länge 64)
     */
    public String encode() {
        String result = "";
        for (Zeile zeile : Zeile.values()) {
            for (Spalte spalte : Spalte.values()) {
                Spielfigur spielfigur = asArray()[spalte.ordinal()][zeile.ordinal()];
                result += (spielfigur == null)? "_" : spielfigur.encode();
            }
        }
        return result;
    }


    public Spielfigur getSchachfigurAnPosition(Position position) {
        final Optional<Spielfeld0> first = spielfelder
                .stream()
                .filter(spielfeld -> spielfeld.position.equals(position))
                .findFirst();
        return (first.isPresent())? (Spielfigur)first.get().spielfigur : null;
    }

    /**
     * Setzt eine Figur <code>figur</code> auf die Spielbrett-Position <code>position</code>
     * @param position Position auf dem Spielfeld (z.B. b4)
     * @param figur die zu setzende Figur (z.B. Lw = weißer Läufer)
     */
    protected void setSchachfigurAnPosition(Position position, Spielfigur figur) {
        Objects.requireNonNull(position, "Argument position is null");
        // FIXME:
        final List<Spielfeld0> spielfelderAnPosition = spielfelder.stream()
                .filter(spielfeld -> spielfeld.position.equals(position))
                .collect(Collectors.toList());

        spielfelder.removeAll(spielfelderAnPosition);
        if (figur != null) {
            spielfelder.add(new Spielfeld(position, figur));
        }
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
     * Ermittelt alle Positionen des Spielfeldes, auf dem eine Spielfigur mit der Farbe
     * <code>farbe</code> steht.
     * @param farbe Farbe, dessen Positionen gesucht werden
     * @return Menge der Positionen, auf dem eine Figur mit Farbe <code>farbe</code> steht
     */
    @JsonIgnore
    public Set<Position> allePositionenMitFarbe(Farbe farbe) {
        return allePositionen().stream()
                .filter(position -> {
                    Spielfigur spielfigur = getSchachfigurAnPosition(position);
                    return spielfigur != null && spielfigur.getFarbe() == farbe;
                })
                .collect(Collectors.toSet());
    }


    /**
     * Ermittelt alle Positionen des Spielbretts
     * @return Alle Positionen des Spielbretts
     */
    @JsonIgnore
    public Set<Position> allePositionen() {
        Set<Position> positionen = new HashSet<>();
        for (Spalte spalte : Spalte.values()) {
            for (Zeile zeile : Zeile.values()) {
                positionen.add(new Position(spalte, zeile));
            }
        }
        return positionen;
    }


    public Position sucheKoenigsPosition(Farbe farbeDesKoenigs) {
        for (Position lfdPos : allePositionenMitFarbe(farbeDesKoenigs)) {
            final Spielfigur spielfigur = getSchachfigurAnPosition(lfdPos);
            if (spielfigur != null && spielfigur.getFigur() == KOENIG) {
                return lfdPos;
            }
        }
        throw new IllegalArgumentException("There is no " + farbeDesKoenigs + " king on the board");
    }


    /**
     * Wendet auf dem Spielbrett einen Halbzug an und gibt das Ergebnis zurück
     * @param halbzug der auf dem Brett anzuwendende Halbzug
     * @return eine neue Instanz des modifizierten Spielbretts
     * @see {@link Halbzug}
     */
    public Spielbrett wendeHalbzugAn(Halbzug halbzug) {
        return new Spielbrett(this) {{
            final Spielfigur spielfigurFrom = getSchachfigurAnPosition(halbzug.getVon());
            setSchachfigurAnPosition(halbzug.getVon(), null);
            setSchachfigurAnPosition(halbzug.getNach(), spielfigurFrom);
        }};
    }

}