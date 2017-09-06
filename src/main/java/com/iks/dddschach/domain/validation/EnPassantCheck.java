package com.iks.dddschach.domain.validation;

import com.iks.dddschach.domain.*;
import com.iks.dddschach.domain.Spielfigur.FigurenTyp;
import com.iks.dddschach.util.IntegerTupel;
import org.apache.commons.lang3.Validate;

import java.util.*;

import static com.iks.dddschach.domain.Farbe.SCHWARZ;
import static com.iks.dddschach.domain.Farbe.WEISS;
import static com.iks.dddschach.domain.Position.Zeile.*;
import static com.iks.dddschach.domain.validation.ValidationUtils.toIntegerTupel;


public class EnPassantCheck implements HalbzugValidation {

    public static class EnPassantCheckResult extends ValidationResult {
        public final Position positionGeschlBauer;

        public EnPassantCheckResult(Zugregel verletzteZugregel) {
            super(false, verletzteZugregel);
            positionGeschlBauer = null;
        }

        public EnPassantCheckResult(Position positionGeschlBauer) {
            super(true, null);
            this.positionGeschlBauer = positionGeschlBauer;
        }
    }


	@Override
	public ValidationResult validiere(Halbzug halbzug, List<Halbzug> halbzugHistorie, Spielbrett spielbrett) {
        Objects.requireNonNull(halbzug, "Argument halbzug is null");
        Objects.requireNonNull(spielbrett, "Argument spielbrett is null");
        Objects.requireNonNull(halbzugHistorie, "Argument zugHistorie is null");

        Spielfigur zugFigur = spielbrett.getSchachfigurAnPosition(halbzug.von);
        Objects.requireNonNull(zugFigur, "There is no figure on " + halbzug.von);

        if (zugFigur.figurTyp != FigurenTyp.BAUER) {
            return new EnPassantCheckResult(Zugregel.HALBZUG_IST_KEIN_EN_PASSANT);
        }
        if (zieheIchVonMitteEinFeldDiagonalNachVorn(halbzug, zugFigur.farbe) &&
            stehtNebenMirEinGegnerischerBauer(halbzug, spielbrett, zugFigur.farbe) &&
            istMeinZielfeldFrei(halbzug, spielbrett)) {

            Validate.isTrue(halbzugHistorie.size() > 0,"No halbzugHistorie present" );

            final Halbzug erwarteterVorgaengerHalbzug = new Halbzug(
                    new Position(halbzug.nach.spalte, zugFigur.farbe == WEISS ? _7 : _2),
                    new Position(halbzug.nach.spalte, zugFigur.farbe == WEISS ? _5 : _4));

            final Halbzug tatsaelicherVorgaengerHalbzug = halbzugHistorie.get(halbzugHistorie.size()-1);

            if (erwarteterVorgaengerHalbzug.equals(tatsaelicherVorgaengerHalbzug)) {
                return new EnPassantCheckResult(tatsaelicherVorgaengerHalbzug.nach);
            }
            else {
                return new EnPassantCheckResult(Zugregel.EN_PASSANT_VORGAENGER_HALBZUG_MUSS_BAUER_SEIN);
            }
        }
        else {
            return new EnPassantCheckResult(Zugregel.HALBZUG_IST_KEIN_EN_PASSANT);
        }
    }


    private boolean istMeinZielfeldFrei(Halbzug halbzug, Spielbrett spielbrett) {
        return spielbrett.getSchachfigurAnPosition(halbzug.nach) == null;
    }

    private boolean stehtNebenMirEinGegnerischerBauer(Halbzug halbzug, Spielbrett spielbrett, Farbe zugFigurFarbe) {
        final Position feldNebenMir = new Position(halbzug.nach.spalte, halbzug.von.zeile);
        final Spielfigur generischerBauer = new Spielfigur(FigurenTyp.BAUER, (zugFigurFarbe == WEISS)? SCHWARZ : WEISS);
        return generischerBauer.equals(spielbrett.getSchachfigurAnPosition(feldNebenMir));
    }

    private boolean zieheIchVonMitteEinFeldDiagonalNachVorn(Halbzug halbzug, Farbe zugFigurFarbe) {
        if ((zugFigurFarbe == WEISS   && halbzug.von.zeile == _5) ||
            (zugFigurFarbe == SCHWARZ && halbzug.von.zeile == _4)) {

            final IntegerTupel diff = toIntegerTupel(halbzug.nach).minus(toIntegerTupel(halbzug.von));
            return diff.abs().x() == 1 && diff.y() == (zugFigurFarbe == WEISS ? 1 : -1);
        }
        return false;
    }


}
