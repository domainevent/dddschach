package com.iks.dddschach.domain.validation;

import com.iks.dddschach.domain.Halbzug;
import com.iks.dddschach.domain.Spielbrett;
import com.iks.dddschach.domain.Spielfigur;
import com.iks.dddschach.domain.Spielfigur.FigurenTyp;
import com.iks.dddschach.util.IntegerTupel;

import java.util.List;
import java.util.Objects;

import static com.iks.dddschach.domain.Farbe.SCHWARZ;
import static com.iks.dddschach.domain.Farbe.WEISS;
import static com.iks.dddschach.domain.Position.Zeile._2;
import static com.iks.dddschach.domain.Position.Zeile._7;


public class BauernRegel implements HalbzugValidation {

	private final static FreieBahnCheck FREIE_BAHN_CHECK = new FreieBahnCheck();

	@Override
	public ValidationResult validiere(Halbzug halbzug, List<Halbzug> halbzugHistorie, Spielbrett spielbrett) {
        Objects.requireNonNull(halbzug, "Argument halbzug is null");
        Objects.requireNonNull(spielbrett, "Argument spielbrett is null");

		Spielfigur zugFigur = spielbrett.getSchachfigurAnPosition(halbzug.von);
        Objects.requireNonNull(zugFigur, "There is no figure on " + halbzug.von);

		if (zugFigur.figurTyp != FigurenTyp.BAUER) {
			throw new IllegalArgumentException("Figure must be a pawn");
		}

		final IntegerTupel from = ValidationUtils.toIntegerTupel(halbzug.von);
        final IntegerTupel to   = ValidationUtils.toIntegerTupel(halbzug.nach);
		final IntegerTupel diff = to.minus(from);
		final IntegerTupel absd = diff.abs();
        final Spielfigur figurFrom = spielbrett.getSchachfigurAnPosition(halbzug.von);

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
        return (diff.y() == 1 && figurFrom.farbe == WEISS) || (diff.y() == -1 && figurFrom.farbe == SCHWARZ);
    }


    private boolean isZweiFeldBedingungAmStartErfuellt(Halbzug zuPruefen, Spielfigur figurFrom, IntegerTupel diff) {
        return (diff.y() == 2 && figurFrom.farbe == WEISS && zuPruefen.von.zeile == _2) ||
               (diff.y() == -2 && figurFrom.farbe == SCHWARZ && zuPruefen.von.zeile == _7);
    }


    private boolean isZielpositionFrei(Halbzug halbzug, Spielbrett spielbrett) {
        return spielbrett.getSchachfigurAnPosition(halbzug.nach) == null;
    }

}
