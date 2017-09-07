package com.javacook.dddschach.domain.validation;

import com.javacook.dddschach.domain.FigurenTyp;
import com.javacook.dddschach.domain.Halbzug$;
import com.javacook.dddschach.domain.Spielbrett$;
import com.javacook.dddschach.domain.Spielfigur$;

import java.util.List;
import java.util.Objects;


/**
 * Überprüft den Versuch, eine eigenen Figur oder einen König zu schlagen.
 */
public class SchlagRegel implements HalbzugValidation {

	@Override
	public ValidationResult validiere(Halbzug$ halbzug, List<Halbzug$> halbzugHistorie, Spielbrett$ spielbrett) {
        Objects.requireNonNull(halbzug, "Argument halbzug is null");
        Objects.requireNonNull(spielbrett, "Argument spielbrett is null");

        Spielfigur$ zugFigur = spielbrett.getSchachfigurAnPosition(halbzug.getVon());
        Objects.requireNonNull(zugFigur, "There is no figure on " + halbzug.getVon());

	    Spielfigur$ figurAmZiel = spielbrett.getSchachfigurAnPosition(halbzug.getNach());

	    if (figurAmZiel == null) {
            return new ValidationResult();
        }
		else if (zugFigur.getFarbe() == figurAmZiel.getFarbe()) {
            return new ValidationResult(Zugregel.EIGENE_FIGUREN_LASSEN_SICH_NICHT_SCHLAGEN);
        }
        else if (figurAmZiel.getFigur() == FigurenTyp.KOENIG) {
            return new ValidationResult(Zugregel.KOENIG_KANN_NICHT_GESCHLAGEN_WERDEN);
        }
        else {
            return new ValidationResult();
        }
	}

}
