package com.iks.dddschach.domain.validation;

import com.iks.dddschach.domain.*;
import com.iks.dddschach.domain.validation.HalbzugValidation.ValidationResult;
import com.iks.dddschach.util.IntegerTupel;

import java.util.List;

import static com.iks.dddschach.domain.Spielfigur.FigurenTyp.KOENIG;
import static com.iks.dddschach.domain.validation.ValidationUtils.toIntegerTupel;
import static com.iks.dddschach.domain.validation.ValidationUtils.toPosition;

/**
 * Checkt, ob sich ein Spieler im Schach befindet
 * @author javacook
 */
public class SchachCheck {

    final static ErreicheZielPruefung ERREICHE_ZIEL_PRUEFUNG = new ErreicheZielPruefung();

	/**
	 * Checkt, ob sich der Spieler mit der Farbe <code>spielerFarbe</code> im Schach befindet.
	 * @param spielerFarbe
	 * @param aktSpielbrett
	 * @return
	 */
	public boolean stehtImSchach(Farbe spielerFarbe, List<Halbzug> zugHistorie, Spielbrett aktSpielbrett) {
        final Position koenigsposition = sucheKoenig(spielerFarbe, aktSpielbrett);
        for (Position.Zeile zeile : Position.Zeile.values()) {
            for (Position.Spalte spalte : Position.Spalte.values()) {
                final Position position = new Position(zeile, spalte);
                final Spielfigur spielfigur = aktSpielbrett.getSchachfigurAnPosition(position);
                if (spielfigur != null && spielfigur.color != spielerFarbe) {
                    final Halbzug halbzugDerKoenigWomoeglichBedroht = new Halbzug(position, koenigsposition);
                    final ValidationResult validationResult =
                            ERREICHE_ZIEL_PRUEFUNG.validiere(halbzugDerKoenigWomoeglichBedroht, zugHistorie, aktSpielbrett);
                    if (validationResult.gueltig) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    Position sucheKoenig(Farbe farbeDesKoenigs, Spielbrett spielbrett) {
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
