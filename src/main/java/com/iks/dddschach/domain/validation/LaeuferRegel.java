package com.iks.dddschach.domain.validation;

import com.iks.dddschach.domain.*;
import com.iks.dddschach.util.IntegerTupel;

import java.util.List;
import java.util.Objects;


public class LaeuferRegel implements HalbzugValidation {

	private final static FreieBahnCheck FREIE_BAHN_CHECK = new FreieBahnCheck();

	@Override
	public ValidationResult validiere(Halbzug$ halbzug, List<? extends Halbzug> halbzugHistorie, Spielbrett$ spielbrett) {
		Objects.requireNonNull(halbzug, "Argument halbzug is null");
		Objects.requireNonNull(spielbrett, "Argument spielbrett is null");

		Spielfigur$ zugFigur = spielbrett.getSchachfigurAnPosition(halbzug.getVon());
		Objects.requireNonNull(zugFigur, "There is no figure on " + halbzug.getNach());
		
		if (zugFigur.getFigur() != FigurenTyp.LAEUFER) {
			throw new IllegalArgumentException("Figure must be a bishop");
		}

        final IntegerTupel from = ValidationUtils.toIntegerTupel(halbzug.getVon());
        final IntegerTupel to = ValidationUtils.toIntegerTupel(halbzug.getNach());
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
