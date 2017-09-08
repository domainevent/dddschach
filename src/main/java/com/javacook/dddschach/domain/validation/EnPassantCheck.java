package com.javacook.dddschach.domain.validation;

import com.javacook.dddschach.domain.*;
import com.javacook.dddschach.util.IntegerTupel;
import org.apache.commons.lang3.Validate;

import java.util.List;
import java.util.Objects;

import static com.javacook.dddschach.domain.Farbe.SCHWARZ;
import static com.javacook.dddschach.domain.Farbe.WEISS;
import static com.javacook.dddschach.domain.validation.ValidationUtils.toIntegerTupel;


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

        Spielfigur zugFigur = spielbrett.getSchachfigurAnPosition(halbzug.getVon());
        Objects.requireNonNull(zugFigur, "There is no figure on " + halbzug.getNach());

        if (zugFigur.getFigur() != FigurenTyp.BAUER) {
            return new EnPassantCheckResult(Zugregel.HALBZUG_IST_KEIN_EN_PASSANT);
        }
        if (zieheIchVonMitteEinFeldDiagonalNachVorn(halbzug, zugFigur.getFarbe()) &&
            stehtNebenMirEinGegnerischerBauer(halbzug, spielbrett, zugFigur.getFarbe()) &&
            istMeinZielfeldFrei(halbzug, spielbrett)) {

            Validate.isTrue(halbzugHistorie.size() > 0,"No getHalbzugHistorie present" );

            final Halbzug erwarteterVorgaengerHalbzug = new Halbzug(
                    new Position(halbzug.getNach().getSpalte(), zugFigur.getFarbe() == WEISS ? Zeile.VII : Zeile.II),
                    new Position(halbzug.getNach().getSpalte(), zugFigur.getFarbe() == WEISS ? Zeile.V : Zeile.IV));

            final Halbzug tatsaelicherVorgaengerHalbzug = (Halbzug)halbzugHistorie.get(halbzugHistorie.size()-1);

            if (erwarteterVorgaengerHalbzug.equals(tatsaelicherVorgaengerHalbzug)) {
                return new EnPassantCheckResult(tatsaelicherVorgaengerHalbzug.getNach());
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
        return spielbrett.getSchachfigurAnPosition(halbzug.getNach()) == null;
    }

    private boolean stehtNebenMirEinGegnerischerBauer(Halbzug halbzug, Spielbrett spielbrett, Farbe zugFigurFarbe) {
        final Position feldNebenMir = new Position(halbzug.getNach().getSpalte(), halbzug.getVon().getZeile());
        final Spielfigur generischerBauer = new Spielfigur(FigurenTyp.BAUER, (zugFigurFarbe == WEISS)? SCHWARZ : WEISS);
        return generischerBauer.equals(spielbrett.getSchachfigurAnPosition(feldNebenMir));
    }

    private boolean zieheIchVonMitteEinFeldDiagonalNachVorn(Halbzug halbzug, Farbe zugFigurFarbe) {
        if ((zugFigurFarbe == WEISS   && halbzug.getVon().getZeile() == Zeile.V) ||
            (zugFigurFarbe == SCHWARZ && halbzug.getVon().getZeile() == Zeile.IV)) {

            final IntegerTupel diff = toIntegerTupel(halbzug.getNach()).minus(toIntegerTupel(halbzug.getVon()));
            return diff.abs().x() == 1 && diff.y() == (zugFigurFarbe == WEISS ? 1 : -1);
        }
        return false;
    }


}
