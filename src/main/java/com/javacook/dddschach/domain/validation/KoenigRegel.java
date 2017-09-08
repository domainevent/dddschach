package com.javacook.dddschach.domain.validation;

import com.javacook.dddschach.domain.FigurenTyp;
import com.javacook.dddschach.domain.Halbzug;
import com.javacook.dddschach.domain.Spielbrett;
import com.javacook.dddschach.domain.Spielfigur;
import com.javacook.dddschach.util.IntegerTupel;

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

		Spielfigur zugFigur = spielbrett.getSchachfigurAnPosition(halbzug.getVon());
		Objects.requireNonNull(zugFigur, "There is no figure on " + halbzug.getNach());

		if (zugFigur.getFigur() != FigurenTyp.KOENIG) {
			throw new IllegalArgumentException("Figure must be a king");
		}

        final IntegerTupel from = ValidationUtils.toIntegerTupel(halbzug.getVon());
        final IntegerTupel to = ValidationUtils.toIntegerTupel(halbzug.getNach());
        final IntegerTupel absd = from.minus(to).abs();

        if (Math.max(absd.x(), absd.y()) == 1) {
			return new ValidationResult();
		}
		else {
            return new ValidationResult(Zugregel.KOENIG_ZIEHT_NUR_EIN_FELD);
        }
	}

}