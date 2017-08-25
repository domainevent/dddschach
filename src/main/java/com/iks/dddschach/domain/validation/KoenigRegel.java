package com.iks.dddschach.domain.validation;

import com.iks.dddschach.olddomain.Halbzug;
import com.iks.dddschach.olddomain.Spielbrett;
import com.iks.dddschach.olddomain.Spielfigur;
import com.iks.dddschach.olddomain.Spielfigur.FigurenTyp;
import com.iks.dddschach.util.IntegerTupel;

import java.util.List;
import java.util.Objects;


/**
 * Prüft, ob der König eine Schritt in seiner Umgebung weiterschreitet.
 */
public class KoenigRegel implements HalbzugValidation {

	private final static FreieBahnCheck FREIE_BAHN_CHECK = new FreieBahnCheck();

	@Override
	public ValidationResult validiere(Halbzug halbzug, List<Halbzug> halbzugHistorie, Spielbrett spielbrett) {
		Objects.requireNonNull(halbzug, "Argument halbzug is null");
		Objects.requireNonNull(spielbrett, "Argument spielbrett is null");

		Spielfigur zugFigur = spielbrett.getSchachfigurAnPosition(halbzug.from);
		Objects.requireNonNull(zugFigur, "There is no figure on " + halbzug.from);

		if (zugFigur.figure != FigurenTyp.KOENIG) {
			throw new IllegalArgumentException("Figure must be a king");
		}

        final IntegerTupel from = ValidationUtils.toIntegerTupel(halbzug.from);
        final IntegerTupel to = ValidationUtils.toIntegerTupel(halbzug.to);
        final IntegerTupel absd = from.minus(to).abs();

        if (Math.max(absd.x(), absd.y()) == 1) {
			return new ValidationResult();
		}
		else {
            return new ValidationResult(Zugregel.KOENIG_ZIEHT_NUR_EIN_FELD);
        }
	}

}
