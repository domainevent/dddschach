package com.iks.dddschach.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.webcohesion.enunciate.metadata.DocumentationExample;
import javafx.scene.paint.Color;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static com.iks.dddschach.domain.Farbe.WEISS;
import static com.iks.dddschach.domain.FigurenTyp.KOENIG;
import static java.lang.Character.toLowerCase;
import static java.lang.Character.toUpperCase;


public class SpielbrettExt extends Spielbrett {

    public SpielbrettExt() {
        super();
    }

    /**
     * Fully-initialising value constructor
     */
    public SpielbrettExt(final List<Spielfeld> spielfelder) {
        super(spielfelder);
    }


//    public SpielbrettExt(final com.iks.dddschach.olddomain.Spielfigur[][] spielfiguren) {
//        final int ANZAHL_SPALTEN = Spalte.values().length;
//        final int ANZAHL_ZEILEN = Zeile.values().length;
//        spielfelder = new ArrayList<>();
//        for (int s = 0; s < ANZAHL_SPALTEN; s++) {
//            for (int z = 0; z < ANZAHL_ZEILEN; z++) {
//                com.iks.dddschach.olddomain.Spielfigur spielfigur = spielfiguren[s][z];
//                if (spielfigur == null) continue;
//                final FigurenTyp figurenTyp = FigurenTyp.valueOf(spielfigur.figure.name());
//                final Farbe color = Farbe.valueOf(spielfigur.color.name());
//                final Spielfigur spielfigur1 = new Spielfigur(figurenTyp, color);
//                final Spielfeld spielfeld = new Spielfeld(
//                        new Position(Zeile.values()[z], Spalte.values()[s]), spielfigur1);
//                spielfelder.add(spielfeld);
//            }
//        }
//    }


    /**
     * Ein zweidimensionales Array (real 8x8) von Spielfiguren
     * @return eine neue Instanz des Spielfiguren-Arrays (immutable)
     * @see {@link Spielfigur}
     */
    @DocumentationExample(exclude = true)
    public Spielfigur[][] getBoard() {
        final int ANZAHL_SPALTEN = Spalte.values().length;
        final int ANZAHL_ZEILEN = Zeile.values().length;
        final Spielfigur[][] copy = new Spielfigur[ANZAHL_SPALTEN][ANZAHL_ZEILEN];

        for (Spielfeld spielfigur : spielfelder) {
            final int z = spielfigur.getPosition().getZeile().ordinal();
            final int s = spielfigur.getPosition().getSpalte().ordinal();
            copy[s][z] = spielfigur.getSpielfigur();
        }
        return copy;
    }

    /**
     * Gibt die Spielposition in möglichst komprimierter Form aus (String der Länge 64)
     */
    public String encode() {
        String result = "";
        for (Zeile zeile : Zeile.values()) {
            for (Spalte spalte : Spalte.values()) {
                Spielfigur figure = getBoard()[spalte.ordinal()][zeile.ordinal()];
                if (figure == null) {
                    result += "_";
                }
                else {
                    char ch = encodeFigure(figure.getFigur());
                    result += (figure.getFarbe() == WEISS)? toUpperCase(ch) : toLowerCase(ch);
                }
            }
        }
        return result;
    }


    public Character encodeFigure(FigurenTyp figur) {
        switch (figur) {
            case KOENIG:  return 'K';
            case DAME:    return 'Q';
            case TURM:    return 'R';
            case LAEUFER: return 'B';
            case SPRINGER: return 'N';
            case BAUER:   return 'P';
        }
        throw new IllegalArgumentException("Unexpected enum " + this);
    }


    public Spielfigur getSchachfigurAnPosition(Position position) {
        final Optional<Spielfeld> first = spielfelder
                .stream()
                .filter(spielfeld -> spielfeld.position.equals(position))
                .findFirst();
        return (first.isPresent())? first.get().spielfigur : null;
    }


    /**
     * Kopier-Konstruktor
     * @param toCopy das zu kopierenden {@link Spielbrett}
     */
    public SpielbrettExt(Spielbrett toCopy) {
        this.spielfelder = new ArrayList<>(toCopy.spielfelder);
    }


    /**
     * Wendet auf dem Spielbrett einen Halbzug an und gibt das Ergebnis zurück
     * @param halbzug der auf dem Brett anzuwendende Halbzug
     * @return eine neue Instanz des modifizierten Spielbretts
     * @see {@link Halbzug}
     */
    public SpielbrettExt wendeHalbzugAn(Halbzug halbzug) {
        return new SpielbrettExt(this) {{
            final Spielfigur spielfigurFrom = getSchachfigurAnPosition(halbzug.getVon());
            setSchachfigurAnPosition(halbzug.getVon(), null);
            setSchachfigurAnPosition(halbzug.getNach(), spielfigurFrom);
        }};
    }


    /**
     * Setzt eine Figur <code>figur</code> auf die Spielbrett-Position <code>position</code>
     * @param position Position auf dem Spielfeld (z.B. b4)
     * @param figur die zu setzende Figur (z.B. Lw = weißer Läufer)
     */
    protected void setSchachfigurAnPosition(Position position, Spielfigur figur) {
        final List<Spielfeld> spielfelderAnPosition = spielfelder.stream()
                .filter(spielfeld -> spielfeld.position.equals(position))
                .collect(Collectors.toList());

        spielfelder.removeAll(spielfelderAnPosition);
        spielfelder.add(new Spielfeld(position, figur));
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
    public Set<Position> getPositionenMitFarbe(Farbe farbe) {
        return getAllePositionen().stream()
                .filter(position -> {
                    Spielfigur spielfigur = getSchachfigurAnPosition(position);
                    return spielfigur != null && spielfigur.getFarbe() == farbe;
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
            if (spielfigur != null && spielfigur.getFigur() == KOENIG) {
                return lfdPos;
            }
        }
        throw new IllegalArgumentException("There is no " + farbeDesKoenigs + " king on the board");
    }


}
