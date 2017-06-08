package com.iks.dddschach.domain.validation;

import com.iks.dddschach.domain.Halbzug;
import com.iks.dddschach.domain.Spielbrett;
import com.iks.dddschach.domain.Spielfigur;
import com.iks.dddschach.domain.base.DomainService;

import java.util.List;
import java.util.Objects;

import static com.iks.dddschach.domain.validation.Zugregel.DER_RICHTIGE_SPIELER_MUSS_AM_ZUG_SEIN;
import static com.iks.dddschach.domain.validation.Zugregel.STARTFELD_MUSS_SPIELFIGUR_ENTHALTEN;


/**
 * Start der Validation. Von hier aus werden alle weiteren Validationen gestartet und verarbeitet.
 */
public class ErreicheZielPruefung implements HalbzugValidation {

    public ValidationResult validiere(
            Halbzug zuPruefen,
            List<Halbzug> zugHistorie,
            Spielbrett aktSpielbrett) {


        final Spielfigur zugFigur = aktSpielbrett.getSchachfigurAnPosition(zuPruefen.from);

        if (!existiertSpielfigurAnStartposition(zugFigur)) {
            return new ValidationResult(false, STARTFELD_MUSS_SPIELFIGUR_ENTHALTEN);
        }

        ValidationResult validationResult;

        switch (zugFigur.figure) {
            case BAUER:
                validationResult = new BauernRegel().validiere(zuPruefen, zugHistorie, aktSpielbrett);
                break;
            case TURM:
                validationResult = new TurmRegel().validiere(zuPruefen, zugHistorie, aktSpielbrett);
                break;
            case SPRINGER:
                validationResult = new SpringerRegel().validiere(zuPruefen, zugHistorie, aktSpielbrett);
                break;
            case LAEUFER:
                validationResult = new LaeuferRegel().validiere(zuPruefen, zugHistorie, aktSpielbrett);
                break;
            case DAME:
                validationResult = new DameRegel().validiere(zuPruefen, zugHistorie, aktSpielbrett);
                break;
            case KOENIG:
                validationResult = new KoenigRegel().validiere(zuPruefen, zugHistorie, aktSpielbrett);
                break;
            default:
                throw new IllegalStateException("Unexpected enum: " + zugFigur.figure);
        }

        return validationResult;
    }

    private boolean existiertSpielfigurAnStartposition(Spielfigur schachfigurAnFrom) {
        return !(schachfigurAnFrom == null);
    }

}
