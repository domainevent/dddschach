package com.iks.dddschach.domain.validation;

import com.iks.dddschach.domain.Halbzug;
import com.iks.dddschach.domain.Spielbrett;
import com.iks.dddschach.domain.Spielfigur;
import com.iks.dddschach.domain.Spielfigur.FigurenTyp;

import java.util.List;
import java.util.Objects;


/**
 * Überprüft den Versuch, eine eigenen Figur oder einen König zu schlagen.
 */
public class SchlagRegel implements HalbzugValidation {

	@Override
	public ValidationResult validiere(Halbzug halbzug, List<Halbzug> halbzugHistorie, Spielbrett spielbrett) {
        Objects.requireNonNull(halbzug, "Argument halbzug is null");
        Objects.requireNonNull(spielbrett, "Argument spielbrett is null");

        Spielfigur zugFigur = spielbrett.getSchachfigurAnPosition(halbzug.from);
        Objects.requireNonNull(zugFigur, "There is no figure on " + halbzug.from);

	    Spielfigur figurAmZiel = spielbrett.getSchachfigurAnPosition(halbzug.to);

	    if (figurAmZiel == null) {
            return new ValidationResult();
        }
		else if (zugFigur.color == figurAmZiel.color) {
            return new ValidationResult(Zugregel.EIGENE_FIGUREN_LASSEN_SICH_NICHT_SCHLAGEN);
        }
        else if (figurAmZiel.figure == FigurenTyp.KOENIG) {
            return new ValidationResult(Zugregel.KOENIG_KANN_NICHT_GESCHLAGEN_WERDEN);
        }
        else {
            return new ValidationResult();
        }
	}

}
