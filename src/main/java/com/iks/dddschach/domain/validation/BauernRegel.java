package com.iks.dddschach.domain.validation;

import java.util.List;

import com.iks.dddschach.domain.*;
import com.iks.dddschach.domain.Spielfigur.FigurenTyp;
import com.iks.dddschach.util.IntegerTupel;

import static com.iks.dddschach.domain.Farbe.SCHWARZ;
import static com.iks.dddschach.domain.Farbe.WEISS;
import static com.iks.dddschach.domain.Position.Spalte._2;
import static com.iks.dddschach.domain.Position.Spalte._7;


public class BauernRegel implements HalbzugValidation {

	private final static FreieBahnCheck FREIE_BAHN_CHECK = new FreieBahnCheck();

	@Override
	public ValidationResult validiere(Halbzug zuPruefen, List<Halbzug> zugHistorie, Spielbrett aktSpielbrett) {
		Spielfigur schachfigur = aktSpielbrett.getSchachfigurAnPosition(zuPruefen.from);
		if (schachfigur.figure != FigurenTyp.BAUER) {
			throw new IllegalArgumentException("Figure must be a pawn");
		}

		final IntegerTupel from = ValidationUtils.toIntegerTupel(zuPruefen.from);
        final IntegerTupel to   = ValidationUtils.toIntegerTupel(zuPruefen.to);
		final IntegerTupel diff = to.minus(from);
		final IntegerTupel absd = diff.abs();
        final Spielfigur figurFrom = aktSpielbrett.getSchachfigurAnPosition(zuPruefen.from);

		if (diff.x() == 0) {
			if (isZweiFeldBedingungAmStartErfuellt(zuPruefen, figurFrom, diff) ||
                isEinfeldBedingungErfuellt(figurFrom, diff)) {

                final ValidationResult freieBahnResult = FREIE_BAHN_CHECK.validiere(zuPruefen, zugHistorie, aktSpielbrett);
                if (!freieBahnResult.gueltig) {
                    return freieBahnResult;
                }

                return (isZielpositionFrei(zuPruefen, aktSpielbrett))?
                    new ValidationResult() :
                    new ValidationResult(Zugregel.BAUER_SCHLAEGT_NUR_SCHRAEG);
			}
            return new ValidationResult(Zugregel.BAUER_ZIEHT_EIN_FELD_VORWAERTS_AUSSER_AM_ANFANG_ZWEI);
		}
		else if (absd.x() == 1) {
            if (isEinfeldBedingungErfuellt(figurFrom, diff)) {
                return (isZielpositionFrei(zuPruefen, aktSpielbrett))?
                        new ValidationResult(Zugregel.BAUER_ZIEHT_NUR_GEREADE_FALLS_ER_NICHT_SCHLAEGT) :
                        new ValidationResult();
            }
            return new ValidationResult(Zugregel.BAUER_ZIEHT_EIN_FELD_VORWAERTS_FALLS_ER_SCHLAEGT);
		}
		else {
			return new ValidationResult(Zugregel.BAUER_ZIEHT_MAXIMAL_EIN_FELD_SEITWAERTS);
		}
	}


    /**
     * Ergibt true, falls sich der Bauer genau um ein Feld noch vorne bewegt hat.
     */
    private boolean isEinfeldBedingungErfuellt(Spielfigur figurFrom, IntegerTupel diff) {
        return (diff.y() == 1 && figurFrom.color == WEISS) || (diff.y() == -1 && figurFrom.color == SCHWARZ);
    }


    private boolean isZweiFeldBedingungAmStartErfuellt(Halbzug zuPruefen, Spielfigur figurFrom, IntegerTupel diff) {
        return (diff.y() == 2 && figurFrom.color == WEISS && zuPruefen.from.vertCoord == _2) ||
               (diff.y() == -2 && figurFrom.color == SCHWARZ && zuPruefen.from.vertCoord == _7);
    }


    private boolean isZielpositionFrei(Halbzug zuPruefen, Spielbrett aktSpielbrett) {
        return aktSpielbrett.getSchachfigurAnPosition(zuPruefen.to) == null;
    }

}
