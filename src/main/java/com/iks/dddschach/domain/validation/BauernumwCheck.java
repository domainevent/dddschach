package com.iks.dddschach.domain.validation;

import com.iks.dddschach.domain.*;

import java.util.*;

import static com.iks.dddschach.domain.Farbe.SCHWARZ;
import static com.iks.dddschach.domain.Farbe.WEISS;
import static com.iks.dddschach.domain.FigurenTyp.DAME;


public class BauernumwCheck implements HalbzugValidation {

	final private static BauernRegel BAUERNREGEL = new BauernRegel();

    public static class BauernumwCheckResult extends ValidationResult {
        public final Spielfigur zielFigur;

        public BauernumwCheckResult(boolean gueltig, Spielfigur zielFigur) {
            super(gueltig, null);
            this.zielFigur = zielFigur;
        }
    }

	@Override
	public ValidationResult validiere(Halbzug$ halbzug, List<? extends Halbzug> halbzugHistorie, Spielbrett$ spielbrett) {
        Objects.requireNonNull(halbzug, "Argument halbzug is null");
        Objects.requireNonNull(spielbrett, "Argument spielbrett is null");
        Objects.requireNonNull(halbzugHistorie, "Argument zugHistorie is null");

        Spielfigur zugFigur = spielbrett.getSchachfigurAnPosition(halbzug.getVon());
        Objects.requireNonNull(zugFigur, "There is no figure on " + halbzug.getVon());

        if (zugFigur.getFigur() != FigurenTyp.BAUER) {
            return new BauernumwCheckResult(false, null);
        }
        final ValidationResult bauernregelResult = BAUERNREGEL.validiere(halbzug, halbzugHistorie, spielbrett);
        if (!bauernregelResult.gueltig) {
            return bauernregelResult;
        }

        if ((zugFigur.getFarbe() == WEISS && halbzug.getVon().getZeile() == Zeile.VII) ||
            (zugFigur.getFarbe() == SCHWARZ && halbzug.getVon().getZeile() == Zeile.II)) {
            return new BauernumwCheckResult(true, new Spielfigur(DAME, zugFigur.getFarbe()));
        }
        return new BauernumwCheckResult(false, null);
    }

}
