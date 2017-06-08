package com.iks.dddschach.domain.validation;

import com.iks.dddschach.domain.Halbzug;
import com.iks.dddschach.domain.Spielbrett;
import com.iks.dddschach.domain.Spielfigur;
import com.iks.dddschach.domain.Spielfigur.FigurenTyp;
import com.iks.dddschach.util.IntegerTupel;

import java.util.List;


public class SpringerRegel implements HalbzugValidation {

	private final static FreieBahnCheck FREIE_BAHN_CHECK = new FreieBahnCheck();

	@Override
	public ValidationResult validiere(Halbzug zuPruefen, List<Halbzug> zugHistorie, Spielbrett aktSpielbrett) {
		Spielfigur schachfigur = aktSpielbrett.getSchachfigurAnPosition(zuPruefen.from);
		if (schachfigur.figure != FigurenTyp.SPRINGER) {
			throw new IllegalArgumentException("Figure must be a knight");
		}

        final IntegerTupel from = ValidationUtils.toIntegerTupel(zuPruefen.from);
        final IntegerTupel to = ValidationUtils.toIntegerTupel(zuPruefen.to);
        final IntegerTupel absd = from.minus(to).abs();

        if ( (absd.x() == 2 && absd.y() == 1) || (absd.x() == 1 && absd.y() == 2) ) {
			return new ValidationResult();
		}
		else {
            return new ValidationResult(Zugregel.SPRINGER_ZIEHT_EINEN_WINKEL);
        }
	}

}
