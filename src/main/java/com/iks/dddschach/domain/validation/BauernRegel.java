package com.iks.dddschach.domain.validation;

import com.iks.dddschach.domain.*;
import com.iks.dddschach.util.IntegerTupel;

import java.util.List;
import java.util.Objects;

import static com.iks.dddschach.domain.Farbe.SCHWARZ;
import static com.iks.dddschach.domain.Farbe.WEISS;


public class BauernRegel implements HalbzugValidation {

	private final static FreieBahnCheck FREIE_BAHN_CHECK = new FreieBahnCheck();

	@Override
	public ValidationResult validiere(Halbzug$ halbzug, List<? extends Halbzug> halbzugHistorie, Spielbrett$ spielbrett) {
        Objects.requireNonNull(halbzug, "Argument halbzug is null");
        Objects.requireNonNull(spielbrett, "Argument spielbrett is null");

		Spielfigur$ zugFigur = spielbrett.getSchachfigurAnPosition(halbzug.getVon());
        Objects.requireNonNull(zugFigur, "There is no figure on " + halbzug.getNach());

		if (zugFigur.getFigur() != FigurenTyp.BAUER) {
			throw new IllegalArgumentException("Figure must be a pawn");
		}

		final IntegerTupel from = ValidationUtils.toIntegerTupel(halbzug.getVon());
        final IntegerTupel to   = ValidationUtils.toIntegerTupel(halbzug.getNach());
		final IntegerTupel diff = to.minus(from);
		final IntegerTupel absd = diff.abs();
        final Spielfigur$ figurFrom = spielbrett.getSchachfigurAnPosition(halbzug.getVon());

		if (diff.x() == 0) {
			if (isZweiFeldBedingungAmStartErfuellt(halbzug, figurFrom, diff) ||
                isEinfeldBedingungErfuellt(figurFrom, diff)) {

                final ValidationResult freieBahnResult = FREIE_BAHN_CHECK.validiere(halbzug, halbzugHistorie, spielbrett);
                if (!freieBahnResult.gueltig) {
                    return freieBahnResult;
                }

                return (isZielpositionFrei(halbzug, spielbrett))?
                    new ValidationResult() :
                    new ValidationResult(Zugregel.BAUER_SCHLAEGT_NUR_SCHRAEG);
			}
            return new ValidationResult(Zugregel.BAUER_ZIEHT_EIN_FELD_VORWAERTS_AUSSER_AM_ANFANG_ZWEI);
		}
		else if (absd.x() == 1) {
            if (isEinfeldBedingungErfuellt(figurFrom, diff)) {
                return (isZielpositionFrei(halbzug, spielbrett))?
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
        return (diff.y() == 1 && figurFrom.getFarbe() == WEISS) || (diff.y() == -1 && figurFrom.getFarbe() == SCHWARZ);
    }


    private boolean isZweiFeldBedingungAmStartErfuellt(Halbzug zuPruefen, Spielfigur figurFrom, IntegerTupel diff) {
        return (diff.y() == 2 && figurFrom.getFarbe() == WEISS && zuPruefen.getVon().getZeile() == Zeile.II) ||
               (diff.y() == -2 && figurFrom.getFarbe() == SCHWARZ && zuPruefen.getVon().getZeile() == Zeile.VII);
    }


    private boolean isZielpositionFrei(Halbzug halbzug, Spielbrett$ spielbrett) {
        return spielbrett.getSchachfigurAnPosition(halbzug.getNach()) == null;
    }

}
