package com.iks.dddschach.domain.validation;

import com.iks.dddschach.domain.Halbzug;
import com.iks.dddschach.domain.Spielbrett;
import com.iks.dddschach.domain.Spielfigur;
import com.iks.dddschach.domain.Spielfigur.FigurenTyp;
import com.iks.dddschach.util.IntegerTupel;

import java.util.List;
import java.util.Objects;


/**
 * @author javacook
 */
public class TurmRegel implements HalbzugValidation {

	private final static FreieBahnCheck FREIE_BAHN_CHECK = new FreieBahnCheck();

	@Override
	public ValidationResult validiere(Halbzug halbzug, List<Halbzug> halbzugHistorie, Spielbrett spielbrett) {
		Objects.requireNonNull(halbzug, "Argument halbzug is null");
		Objects.requireNonNull(spielbrett, "Argument spielbrett is null");

		Spielfigur zugFigur = spielbrett.getSchachfigurAnPosition(halbzug.von);
		Objects.requireNonNull(zugFigur, "There is no figure on " + halbzug.von);

		if (zugFigur.figurTyp != FigurenTyp.TURM) {
			throw new IllegalArgumentException("Figure must be a rook");
		}

        final IntegerTupel from = ValidationUtils.toIntegerTupel(halbzug.von);
        final IntegerTupel to   = ValidationUtils.toIntegerTupel(halbzug.nach);
        final IntegerTupel absd = from.minus(to).abs();

        // Turm darf nur geradlinig ziehen
        if (absd.x() != 0 && absd.y() != 0) {
            return new ValidationResult(Zugregel.TURM_ZIEHT_GERADE);
        }

        final ValidationResult freieBahnResult = FREIE_BAHN_CHECK.validiere(halbzug, halbzugHistorie, spielbrett);
		if (!freieBahnResult.gueltig) {
		    return freieBahnResult;
        }

        return new ValidationResult();
	}

}
