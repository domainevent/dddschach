package com.iks.dddschach.domain.validation;

import com.iks.dddschach.domain.Halbzug;
import com.iks.dddschach.domain.Spielbrett;
import com.iks.dddschach.domain.Spielfigur;
import com.iks.dddschach.domain.Spielfigur.FigurenTyp;
import com.iks.dddschach.util.IntegerTupel;

import java.util.List;


public class KoenigRegel implements HalbzugValidation {

	private final static FreieBahnCheck FREIE_BAHN_CHECK = new FreieBahnCheck();

	@Override
	public ValidationResult validiere(Halbzug zuPruefen, List<Halbzug> zugHistorie, Spielbrett aktSpielbrett) {
		Spielfigur schachfigur = aktSpielbrett.getSchachfigurAnPosition(zuPruefen.from);
		if (schachfigur.figure != FigurenTyp.KOENIG) {
			throw new IllegalArgumentException("Figure must be a king");
		}

        final IntegerTupel from = ValidationUtils.toIntegerTupel(zuPruefen.from);
        final IntegerTupel to = ValidationUtils.toIntegerTupel(zuPruefen.to);
        final IntegerTupel absd = from.minus(to).abs();

        if (Math.max(absd.x(), absd.y()) == 1) {
			return new ValidationResult();
		}
		else {
            return new ValidationResult(Zugregel.KOENIG_ZIEHT_NUR_EIN_FELD);
        }
	}

}
