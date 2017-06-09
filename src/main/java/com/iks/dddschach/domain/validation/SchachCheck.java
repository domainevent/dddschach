package com.iks.dddschach.domain.validation;

import com.iks.dddschach.domain.*;
import com.iks.dddschach.domain.validation.HalbzugValidation.ValidationResult;
import com.iks.dddschach.util.IntegerTupel;

import java.util.List;
import java.util.Objects;

import static com.iks.dddschach.domain.Spielfigur.FigurenTyp.KOENIG;
import static com.iks.dddschach.domain.validation.ValidationUtils.toIntegerTupel;
import static com.iks.dddschach.domain.validation.ValidationUtils.toPosition;

/**
 * Checkt, ob sich der ziehende Spieler nach seinem Halbzug (noch) im Schach befindet
 * @author javacook
 */
public class SchachCheck implements HalbzugValidation {

	/**
	 * Checkt, ob sich der Spieler, wenn er den Halbzug <code>halbzug</code> zöge, danach noch im Schach befände.
     * Dann dürfte er diesen Halbzug nicht ausführen.
	 */
    @Override
    public ValidationResult validiere(final Halbzug halbzug,
                                      final List<Halbzug> zugHistorie,
                                      final Spielbrett spielbrett) {

        Objects.requireNonNull(halbzug, "Argument halbzug is null");
        Objects.requireNonNull(spielbrett, "Argument spielbrett is null");

        final Farbe spielerFarbe = ermittelSpielerFarbe(halbzug, spielbrett);
        final Spielbrett brettMitSimulHalbzug = spielbrett.wendeHalbzugAn(halbzug);

        final Position koenigsposition = sucheKoenig(spielerFarbe, brettMitSimulHalbzug);

        // Gehe alle Figuren des Gegners durch und prüfe, ob diese meinen König schlagen könnten:
        for (Position.Zeile zeile : Position.Zeile.values()) {
            for (Position.Spalte spalte : Position.Spalte.values()) {
                final Position position = new Position(zeile, spalte);
                final Spielfigur spielfigur = brettMitSimulHalbzug.getSchachfigurAnPosition(position);
                if (isGegnerischeFigur(spielfigur, spielerFarbe)) {
                    final Halbzug halbzugDerKoenigWomoeglichBedroht = new Halbzug(position, koenigsposition);

                    if (koennteMeinKoenigGeschlagenWerden(halbzugDerKoenigWomoeglichBedroht,
                            zugHistorie, brettMitSimulHalbzug)) {
                        return new ValidationResult(Zugregel.KOENIG_STEHT_IM_SCHACH);
                    }
                }
            }
        }
        return new ValidationResult();
    }


    private boolean isGegnerischeFigur(Spielfigur spielfigur, Farbe spielerFarbe) {
        return spielfigur != null && spielfigur.color != spielerFarbe;
    }

    private final static ErreicheZielPruefung ERREICHE_ZIEL_PRUEFUNG = new ErreicheZielPruefung();

    private boolean koennteMeinKoenigGeschlagenWerden(final Halbzug halbzugDerKoenigWomoeglichBedroht,
                                                      final List<Halbzug> zugHistorie,
                                                      final Spielbrett brettMitSimulHalbzug) {
        return ERREICHE_ZIEL_PRUEFUNG.validiere(
                halbzugDerKoenigWomoeglichBedroht, zugHistorie, brettMitSimulHalbzug).gueltig;
    }

    private Farbe ermittelSpielerFarbe(Halbzug halbzug, Spielbrett spielbrett) {
        final Spielfigur zugFigur = spielbrett.getSchachfigurAnPosition(halbzug.from);
        Objects.requireNonNull(zugFigur, "There is no figure on " + halbzug.from);
        return zugFigur.color;
    }

    private Position sucheKoenig(Farbe farbeDesKoenigs, Spielbrett spielbrett) {
	    for (Position.Zeile zeile : Position.Zeile.values()) {
            for (Position.Spalte spalte : Position.Spalte.values()) {
                final Position position = new Position(zeile, spalte);
                final Spielfigur spielfigur = spielbrett.getSchachfigurAnPosition(position);
                if (spielfigur != null && spielfigur.figure == KOENIG && spielfigur.color == farbeDesKoenigs) {
                    return position;
                }
            }
        }
        throw new IllegalArgumentException("There is no " + farbeDesKoenigs + " king on the board");
    }


}
