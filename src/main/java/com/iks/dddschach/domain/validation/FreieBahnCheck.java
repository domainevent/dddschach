package com.iks.dddschach.domain.validation;

import static com.iks.dddschach.domain.validation.ValidationUtils.toIntegerTupel;
import static com.iks.dddschach.domain.validation.ValidationUtils.toPosition;

import java.util.List;

import com.iks.dddschach.domain.Halbzug;
import com.iks.dddschach.domain.Position;
import com.iks.dddschach.domain.Spielbrett;
import com.iks.dddschach.util.IntegerTupel;


/**
 * Checkt, ob sich <i>zwischen</i> der Start- und Ziel-Position eines Halbzugs Figuren stehen.
 * @author javacook
 */
public class FreieBahnCheck implements HalbzugValidation {

	@Override
	public ValidationResult validiere(Halbzug zuPruefen, List<Halbzug> zugHistorie, Spielbrett aktSpielbrett) {
        final boolean success = success(zuPruefen, aktSpielbrett);
        return success? new ValidationResult() : new ValidationResult(Zugregel.ZUG_STRECKE_MUSS_FREI_SEIN);
	}

    /**
     * Überprüft, ob sich auf den Spielbrettpositionen innerhalb des Halbzuges (d.h. ohne
     * Berücksichtigung von Start und End-Position) Spielfiguren befinden.
     */
	boolean success(Halbzug zuPruefen, Spielbrett aktSpielbrett) {
		System.out.println(zuPruefen);
        final IntegerTupel from = toIntegerTupel(zuPruefen.from);
        final IntegerTupel to = toIntegerTupel(zuPruefen.to);

		if (IntegerTupel.maxNorm(from, to) <=1) return true;

		final IntegerTupel middel = IntegerTupel.middel(from, to);
        final Position posMid = toPosition(middel);

        if (aktSpielbrett.getSchachfigurAnPosition(posMid) != null) return false;

		return success(new Halbzug(zuPruefen.from, posMid), aktSpielbrett) &&
			   success(new Halbzug(posMid, zuPruefen.to), aktSpielbrett);
	}

}
