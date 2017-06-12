package com.iks.dddschach.domain.validation;

import com.iks.dddschach.domain.*;
import com.iks.dddschach.domain.Position.Spalte;
import com.iks.dddschach.domain.Position.Zeile;

import java.util.List;
import java.util.Objects;

import static com.iks.dddschach.domain.Spielfigur.FigurenTyp.KOENIG;


/**
 * Checkt, ob sich der ziehende Spieler nach seinem Halbzug (noch) im Schach befindet
 * @author javacook
 */
public class SchachCheck implements HalbzugValidation {

    private final static ErreicheZielCheck ERREICHE_ZIEL_CHECK = new ErreicheZielCheck();

	/**
	 * Checkt, ob sich der Spieler, wenn er den Halbzug <code>halbzug</code> zöge, danach noch im Schach befände.
     * Dann dürfte er diesen Halbzug nicht ausführen.
	 */
    @Override
    public ValidationResult validiere(final Halbzug halbzug,
                                      final List<Halbzug> halbzugHistorie,
                                      final Spielbrett spielbrett) {

        Objects.requireNonNull(halbzug, "Argument halbzug is null");
        Objects.requireNonNull(spielbrett, "Argument spielbrett is null");

        final Farbe spielerFarbe = ermittelSpielerFarbe(halbzug, spielbrett);
        final Spielbrett brettMitSimulHalbzug = spielbrett.wendeHalbzugAn(halbzug);

        final Position koenigsposition = sucheKoenig(spielerFarbe, brettMitSimulHalbzug);

        // Gehe alle Figuren des Gegners durch und prüfe, ob diese meinen König schlagen könnten:
        for (Spalte spalte : Spalte.values()) {
            for (Zeile zeile : Zeile.values()) {
                final Position lfdPos = new Position(spalte, zeile);
                final Spielfigur spielfigur = spielbrett.getSchachfigurAnPosition(lfdPos);
                if (isGegnerischeFigur(spielfigur, spielerFarbe)) {
                    final Halbzug lfdHalbzug = new Halbzug(lfdPos, koenigsposition);
                    if (istZielDesHalbzugsBedroht(lfdHalbzug, halbzugHistorie, spielbrett)) {
                        return new ValidationResult(Zugregel.KOENIG_STEHT_IM_SCHACH);
                    }
                }
            }
        }
        return new ValidationResult();
    }


    private boolean istZielDesHalbzugsBedroht(final Halbzug halbzug,
                                              final List<Halbzug> halbzugHistorie,
                                              final Spielbrett spielbrett) {
        return ERREICHE_ZIEL_CHECK.validiere(halbzug, halbzugHistorie, spielbrett).gueltig;
    }


    /**
     * Ermittelt, ob <code>spielfigur</code> eine gegnerischen Figur ist in Bezug auf
     * dem Spieler (mit der Farbe <code>spielerFarbe</code>).
     */
    private boolean isGegnerischeFigur(Spielfigur spielfigur, Farbe spielerFarbe) {
        return spielfigur != null && spielfigur.color != spielerFarbe;
    }


    /**
     * Ermittelt die Farbe des Spielers, der den Halbzug <code>halbzug</code> ausführen will
     */
    private Farbe ermittelSpielerFarbe(Halbzug halbzug, Spielbrett spielbrett) {
        final Spielfigur zugFigur = spielbrett.getSchachfigurAnPosition(halbzug.from);
        Objects.requireNonNull(zugFigur, "There is no figure on " + halbzug.from);
        return zugFigur.color;
    }


    private Position sucheKoenig(Farbe farbeDesKoenigs, Spielbrett spielbrett) {
	    for (Spalte spalte : Spalte.values()) {
            for (Zeile zeile : Zeile.values()) {
                final Position position = new Position(spalte, zeile);
                final Spielfigur spielfigur = spielbrett.getSchachfigurAnPosition(position);
                if (spielfigur != null && spielfigur.figure == KOENIG && spielfigur.color == farbeDesKoenigs) {
                    return position;
                }
            }
        }
        throw new IllegalArgumentException("There is no " + farbeDesKoenigs + " king on the board");
    }

}
