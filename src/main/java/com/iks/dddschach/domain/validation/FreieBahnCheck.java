package com.iks.dddschach.domain.validation;

import static com.iks.dddschach.domain.validation.ValidationUtils.toIntegerTupel;

import java.util.List;
import java.util.Objects;

import com.iks.dddschach.util.IntegerTupel;


/**
 * Checkt, ob sich <i>zwischen</i> der Start- und Ziel-Position eines Halbzugs Figuren befinden.
 * @author javacook
 */
public class FreieBahnCheck implements HalbzugValidation {

	@Override
	public ValidationResult validiere(Halbzug halbzug, List<Halbzug> halbzugHistorie, Spielbrett spielbrett) {
		Objects.requireNonNull(halbzug, "Argument halbzug is null");
		Objects.requireNonNull(spielbrett, "Argument spielbrett is null");

        final boolean success = success(halbzug, spielbrett);
        return success? new ValidationResult() : new ValidationResult(Zugregel.ZUG_STRECKE_MUSS_FREI_SEIN);
	}

    /**
     * Überprüft, ob sich auf den Spielbrettpositionen innerhalb des Halbzuges (d.h. ohne
     * Berücksichtigung von Start und End-Position) Spielfiguren befinden.
     */
	boolean success(Halbzug halbzug, Spielbrett spielbrett) {
        final IntegerTupel from = toIntegerTupel(halbzug.from);
        final IntegerTupel to = toIntegerTupel(halbzug.to);

		if (IntegerTupel.maxNorm(from, to) <=1) return true;

        final Position posMid = ValidationUtils.middle(halbzug.from, halbzug.to);
        if (spielbrett.getSchachfigurAnPosition(posMid) != null) {
        	return false;
		}
		return success(new Halbzug(halbzug.from, posMid), spielbrett) &&
			   success(new Halbzug(posMid, halbzug.to), spielbrett);
	}

}
