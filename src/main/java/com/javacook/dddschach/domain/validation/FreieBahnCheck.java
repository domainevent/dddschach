package com.javacook.dddschach.domain.validation;

import com.javacook.dddschach.domain.Halbzug;
import com.javacook.dddschach.domain.Position;
import com.javacook.dddschach.domain.Spielbrett;
import com.javacook.dddschach.util.IntegerTupel;

import java.util.List;
import java.util.Objects;

import static com.javacook.dddschach.domain.validation.ValidationUtils.toIntegerTupel;


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
        final IntegerTupel from = toIntegerTupel(halbzug.getVon());
        final IntegerTupel to = toIntegerTupel(halbzug.getNach());

		if (IntegerTupel.maxNorm(from, to) <=1) return true;

        final Position posMid = ValidationUtils.middle(halbzug.getVon(), halbzug.getNach());
        if (spielbrett.getSchachfigurAnPosition(posMid) != null) {
        	return false;
		}
		return success(new Halbzug(halbzug.getVon(), posMid), spielbrett) &&
			   success(new Halbzug(posMid, halbzug.getNach()), spielbrett);
	}

}
