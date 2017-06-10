package com.iks.dddschach.domain.validation;

import com.iks.dddschach.domain.*;
import com.iks.dddschach.domain.Spielfigur.FigurenTyp;

import java.util.*;
import java.util.stream.Collectors;

import static com.iks.dddschach.domain.Farbe.SCHWARZ;
import static com.iks.dddschach.domain.Farbe.WEISS;
import static com.iks.dddschach.domain.Position.Spalte.*;
import static com.iks.dddschach.domain.Position.Zeile.*;
import static com.iks.dddschach.domain.Spielfigur.FigurenTyp.DAME;


public class BauernumwandlungCheck implements HalbzugValidation {

	final static BauernRegel BAUERNREGEL = new BauernRegel();

    public static class BauernumwandlungCheckResult extends ValidationResult {
        public final Spielfigur zielFigur;

        public BauernumwandlungCheckResult(boolean gueltig, Spielfigur zielFigur) {
            super(gueltig, null);
            this.zielFigur = zielFigur;
        }
    }

	@Override
	public ValidationResult validiere(Halbzug halbzug, List<Halbzug> zugHistorie, Spielbrett spielbrett) {
        Objects.requireNonNull(halbzug, "Argument halbzug is null");
        Objects.requireNonNull(spielbrett, "Argument spielbrett is null");
        Objects.requireNonNull(zugHistorie, "Argument zugHistorie is null");

        Spielfigur zugFigur = spielbrett.getSchachfigurAnPosition(halbzug.from);
        Objects.requireNonNull(zugFigur, "There is no figure on " + halbzug.from);

        if (zugFigur.figure != FigurenTyp.BAUER) {
            return new BauernumwandlungCheckResult(false, null);
        }
        final ValidationResult bauernregelResult = BAUERNREGEL.validiere(halbzug, zugHistorie, spielbrett);
        if (!bauernregelResult.gueltig) {
            return bauernregelResult;
        }

        if ((zugFigur.color == WEISS && halbzug.from.vertCoord == _7) ||
            (zugFigur.color == SCHWARZ && halbzug.from.vertCoord == _2)) {
            return new BauernumwandlungCheckResult(true, new Spielfigur(DAME, zugFigur.color));
        }
        return new BauernumwandlungCheckResult(false, null);
    }

}
