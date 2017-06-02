package com.iks.dddschach.domain.validation;

import java.util.List;

import com.iks.dddschach.domain.Halbzug;
import com.iks.dddschach.domain.Spielbrett;
import com.iks.dddschach.domain.Spielfigur;
import com.iks.dddschach.domain.Spielfigur.FigurenTyp;

public class BauernRegel implements HalbzugValidation {

	private final static FreieBahnCheck FREIE_BAHN_CHECK = new FreieBahnCheck();

	@Override
	public ValidationResult validiere(Halbzug zuPruefen, List<Halbzug> zugHistorie, Spielbrett aktSpielbrett) {
		Spielfigur schachfigur = aktSpielbrett.getSchachfigurAnPosition(zuPruefen.from);
		if (schachfigur.figure != FigurenTyp.BAUER) {
			throw new IllegalArgumentException("Figure must be a pawn");
		}
        final ValidationResult freieBahnResult = FREIE_BAHN_CHECK.validiere(zuPruefen, zugHistorie, aktSpielbrett);
		if (!freieBahnResult.gueltig) {
		    return freieBahnResult;
        }

        return new ValidationResult();
	}

}
