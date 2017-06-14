package com.iks.dddschach.domain.validation;

import com.iks.dddschach.domain.Halbzug;
import com.iks.dddschach.domain.Spielbrett;
import com.iks.dddschach.domain.Spielfigur;
import com.iks.dddschach.domain.Spielfigur.FigurenTyp;
import com.iks.dddschach.util.IntegerTupel;

import java.util.List;
import java.util.Objects;


public class DameRegel implements HalbzugValidation {

	private final static FreieBahnCheck FREIE_BAHN_CHECK = new FreieBahnCheck();

	@Override
	public ValidationResult validiere(Halbzug halbzug, List<Halbzug> halbzugHistorie, Spielbrett spielbrett) {
		Objects.requireNonNull(halbzug, "Argument halbzug is null");
		Objects.requireNonNull(spielbrett, "Argument spielbrett is null");

		Spielfigur zugFigur = spielbrett.getSchachfigurAnPosition(halbzug.from);
        Objects.requireNonNull(zugFigur, "There is no figure on " + halbzug.from);

		if (zugFigur.figure != FigurenTyp.DAME) {
			throw new IllegalArgumentException("Figure must be a queen");
		}

        final IntegerTupel from = ValidationUtils.toIntegerTupel(halbzug.from);
        final IntegerTupel to = ValidationUtils.toIntegerTupel(halbzug.to);
        final IntegerTupel absd = from.minus(to).abs();

        // Diagonal und Gerade-Prüfung
        if (absd.x() != absd.y() && absd.x() != 0 && absd.y() != 0) {
            return new ValidationResult(Zugregel.DAME_ZIEHT_GERADE_ODER_DIAGONAL);
        }

        final ValidationResult freieBahnResult = FREIE_BAHN_CHECK.validiere(halbzug, halbzugHistorie, spielbrett);
		if (!freieBahnResult.gueltig) {
		    return freieBahnResult;
        }

        return new ValidationResult();
	}

}