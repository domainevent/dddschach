package com.iks.dddschach.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


public class SchachpartieExt extends Schachpartie {

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


}
