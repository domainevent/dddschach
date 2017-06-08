package com.iks.dddschach.domain.validation;

import com.iks.dddschach.domain.Halbzug;
import com.iks.dddschach.domain.Spielbrett;
import com.iks.dddschach.domain.Spielfigur;
import com.iks.dddschach.domain.base.DomainService;

import java.util.List;
import java.util.Objects;

import static com.iks.dddschach.domain.validation.Zugregel.DER_RICHTIGE_SPIELER_MUSS_AM_ZUG_SEIN;


/**
 * Start der Validation. Von hier aus werden alle weiteren Validationen gestartet und verarbeitet.
 */
public class HalbzugValidator implements HalbzugValidation, DomainService {

    final static ErreicheZielPruefung ERREICHE_ZIEL_PRUEFUNG = new ErreicheZielPruefung();
    final static SchlagRegel SCHLAG_REGEL = new SchlagRegel();
    final static SchachCheck SCHACH_CHECK = new SchachCheck();

    public ValidationResult validiere(
            Halbzug zuPruefen,
            List<Halbzug> zugHistorie,
            Spielbrett aktSpielbrett) {


        final Spielfigur zugFigur = aktSpielbrett.getSchachfigurAnPosition(zuPruefen.from);

        if (!istRichtigerSpielerAmZug(zugHistorie, zugFigur)) {
            return new ValidationResult(false, DER_RICHTIGE_SPIELER_MUSS_AM_ZUG_SEIN);
        }

        ValidationResult zielErreichbarResult = ERREICHE_ZIEL_PRUEFUNG.validiere(zuPruefen, zugHistorie, aktSpielbrett);
        if (!zielErreichbarResult.gueltig) return zielErreichbarResult;

        ValidationResult schlagRegelResult = SCHLAG_REGEL.validiere(zuPruefen, zugHistorie, aktSpielbrett);
        if (!schlagRegelResult.gueltig) return schlagRegelResult;

        final Spielbrett simuliereHalbzug = aktSpielbrett.wendeHalbzugAn(zuPruefen);
        if (SCHACH_CHECK.stehtImSchach(zugFigur.color, zugHistorie, simuliereHalbzug)) {
            return new ValidationResult(Zugregel.KOENIG_STEHT_IM_SCHACH);
        }

        return new ValidationResult();
    }


    private boolean istRichtigerSpielerAmZug(List<Halbzug> zugHistorie, Spielfigur schachfigurAnFrom) {
        Objects.requireNonNull(schachfigurAnFrom);
        return (schachfigurAnFrom.color.ordinal() == zugHistorie.size() % 2);
        // return true;
    }


    private boolean existiertSpielfigurAnStartposition(Spielfigur schachfigurAnFrom) {
        return !(schachfigurAnFrom == null);
    }



}
