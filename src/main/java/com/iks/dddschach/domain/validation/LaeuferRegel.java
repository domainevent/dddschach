package com.iks.dddschach.domain.validation;

import com.iks.dddschach.domain.Halbzug;
import com.iks.dddschach.domain.Spielbrett;
import com.iks.dddschach.domain.Spielfigur;
import com.iks.dddschach.domain.Spielfigur.FigurenTyp;
import com.iks.dddschach.util.IntegerTupel;

import java.util.List;


public class LaeuferRegel implements HalbzugValidation {

	private final static FreieBahnCheck FREIE_BAHN_CHECK = new FreieBahnCheck();

	@Override
	public ValidationResult validiere(Halbzug zuPruefen, List<Halbzug> zugHistorie, Spielbrett aktSpielbrett) {
		Spielfigur schachfigur = aktSpielbrett.getSchachfigurAnPosition(zuPruefen.from);
		if (schachfigur.figure != FigurenTyp.LAEUFER) {
			throw new IllegalArgumentException("Figure must be a bishop");
		}

        final IntegerTupel from = ValidationUtils.toIntegerTupel(zuPruefen.from);
        final IntegerTupel to = ValidationUtils.toIntegerTupel(zuPruefen.to);
        final IntegerTupel abs = from.minus(to).abs();

        // Diagonalpruefung
        if (abs.x() != abs.y()) {
            return new ValidationResult(Zugregel.LAUEFER_ZIEHT_DIAGONAL);
        }

        final ValidationResult freieBahnResult = FREIE_BAHN_CHECK.validiere(zuPruefen, zugHistorie, aktSpielbrett);
		if (!freieBahnResult.gueltig) {
		    return freieBahnResult;
        }

        return new ValidationResult();
	}

}
