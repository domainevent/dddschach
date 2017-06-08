package com.iks.dddschach.domain.validation;

import com.iks.dddschach.domain.Halbzug;
import com.iks.dddschach.domain.Spielbrett;
import com.iks.dddschach.domain.Spielfigur;
import com.iks.dddschach.domain.Spielfigur.FigurenTyp;

import java.util.List;
import java.util.Objects;


/**
 * Überprüft, ob versucht wird eine eigenen Figur oder einen König zu schlagen.
 */
public class SchlagRegel implements HalbzugValidation {


	@Override
	public ValidationResult validiere(Halbzug zuPruefen, List<Halbzug> zugHistorie, Spielbrett aktSpielbrett) {
        Spielfigur schachfigurFrom = Objects.requireNonNull(aktSpielbrett.getSchachfigurAnPosition(zuPruefen.from));
	    Spielfigur schachfigurTo = aktSpielbrett.getSchachfigurAnPosition(zuPruefen.to);

	    if (schachfigurTo == null) {
            return new ValidationResult();
        }
		else if (schachfigurFrom.color == schachfigurTo.color) {
            return new ValidationResult(Zugregel.EIGENE_FIGUREN_LASSEN_SICH_NICHT_SCHLAGEN);
        }
        else if (schachfigurTo.figure == FigurenTyp.KOENIG) {
            return new ValidationResult(Zugregel.KOENIG_KANN_NICHT_GESCHLAGEN_WERDEN);
        }
        else {
            return new ValidationResult();
        }
	}

}
