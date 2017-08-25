package com.iks.dddschach.domain.validation;

import com.iks.dddschach.olddomain.Halbzug;
import com.iks.dddschach.olddomain.Spielbrett;
import com.iks.dddschach.olddomain.Spielfigur;
import com.iks.dddschach.olddomain.Spielfigur.FigurenTyp;
import com.iks.dddschach.util.IntegerTupel;

import java.util.List;
import java.util.Objects;


/**
 * Überprüft, ob der Springer einen 2x1- bzw. 1x2-Haken zieht
 */
public class SpringerRegel implements HalbzugValidation {

	@Override
	public ValidationResult validiere(Halbzug halbzug, List<Halbzug> halbzugHistorie, Spielbrett spielbrett) {
		Objects.requireNonNull(halbzug, "Argument halbzug is null");
		Objects.requireNonNull(spielbrett, "Argument spielbrett is null");

		Spielfigur zugFigur = spielbrett.getSchachfigurAnPosition(halbzug.from);
		Objects.requireNonNull(zugFigur, "There is no figure on " + halbzug.from);

		if (zugFigur.figure != FigurenTyp.SPRINGER) {
			throw new IllegalArgumentException("Figure must be a knight");
		}

        final IntegerTupel from = ValidationUtils.toIntegerTupel(halbzug.from);
        final IntegerTupel to = ValidationUtils.toIntegerTupel(halbzug.to);
        final IntegerTupel absd = from.minus(to).abs();

        if ( (absd.x() == 2 && absd.y() == 1) || (absd.x() == 1 && absd.y() == 2) ) {
			return new ValidationResult();
		}
		else {
            return new ValidationResult(Zugregel.SPRINGER_ZIEHT_EINEN_WINKEL);
        }
	}

}
