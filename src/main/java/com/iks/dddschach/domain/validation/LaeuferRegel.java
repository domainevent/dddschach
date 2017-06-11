package com.iks.dddschach.domain.validation;

import com.iks.dddschach.domain.Halbzug;
import com.iks.dddschach.domain.Spielbrett;
import com.iks.dddschach.domain.Spielfigur;
import com.iks.dddschach.domain.Spielfigur.FigurenTyp;
import com.iks.dddschach.util.IntegerTupel;

import java.util.List;
import java.util.Objects;


public class LaeuferRegel implements HalbzugValidation {

	private final static FreieBahnCheck FREIE_BAHN_CHECK = new FreieBahnCheck();

	@Override
	public ValidationResult validiere(Halbzug halbzug, List<Halbzug> halbzugHistorie, Spielbrett spielbrett) {
		Objects.requireNonNull(halbzug, "Argument halbzug is null");
		Objects.requireNonNull(spielbrett, "Argument spielbrett is null");

		Spielfigur zugFigur = spielbrett.getSchachfigurAnPosition(halbzug.from);
		Objects.requireNonNull(zugFigur, "There is no figure on " + halbzug.from);
		
		if (zugFigur.figure != FigurenTyp.LAEUFER) {
			throw new IllegalArgumentException("Figure must be a bishop");
		}

        final IntegerTupel from = ValidationUtils.toIntegerTupel(halbzug.from);
        final IntegerTupel to = ValidationUtils.toIntegerTupel(halbzug.to);
        final IntegerTupel absd = from.minus(to).abs();

        // Diagonalpruefung
        if (absd.x() != absd.y()) {
            return new ValidationResult(Zugregel.LAUEFER_ZIEHT_DIAGONAL);
        }

        final ValidationResult freieBahnResult = FREIE_BAHN_CHECK.validiere(halbzug, halbzugHistorie, spielbrett);
		if (!freieBahnResult.gueltig) {
		    return freieBahnResult;
        }

        return new ValidationResult();
	}

}
