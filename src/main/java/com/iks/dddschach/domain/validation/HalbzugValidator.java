package com.iks.dddschach.domain.validation;

import com.iks.dddschach.domain.Halbzug;
import com.iks.dddschach.domain.Spielbrett;
import com.iks.dddschach.domain.Spielfigur;
import com.iks.dddschach.domain.base.DomainService;

import java.util.List;
import java.util.Objects;

import static com.iks.dddschach.domain.validation.Zugregel.SPIELER_MUSS_AM_ZUG_SEIN;
import static com.iks.dddschach.domain.validation.Zugregel.SPIELFIGUR_MUSS_EXISTIEREN;


/**
 * Start der Validation. Von hier aus werden alle weiteren Validationen gestartet und verarbeitet.
 */
public class HalbzugValidator implements HalbzugValidation, DomainService {

    public ValidationResult validiere(
            Halbzug zuPruefen,
            List<Halbzug> zugHistorie,
            Spielbrett aktSpielbrett) {


        final Spielfigur zugFigur = aktSpielbrett.getSchachfigurAnPosition(zuPruefen.from);

        if (!existiertSpielfigurAnStartposition(zugFigur)) {
            return new ValidationResult(false, SPIELFIGUR_MUSS_EXISTIEREN);
        }

        if (!istRichtigerSpielerAmZug(zugHistorie, zugFigur)) {
            return new ValidationResult(false, SPIELER_MUSS_AM_ZUG_SEIN);
        }

        ValidationResult validationResult = new ValidationResult();

        switch (zugFigur.figure) {
            case BAUER:
                validationResult = new BauernRegel().validiere(zuPruefen, zugHistorie, aktSpielbrett);
                break;
            case TURM:
                break;
            case SPRINGER:
                break;
            case LAEUFER:
                validationResult = new LaeuferRegel().validiere(zuPruefen, zugHistorie, aktSpielbrett);
                break;
            case DAME:
                break;
            case KOENIG:
                break;
            default:
                throw new IllegalStateException("Unexpected enum: " + zugFigur.figure);
        }

        return validationResult;
    }


    private boolean istRichtigerSpielerAmZug(List<Halbzug> zugHistorie, Spielfigur schachfigurAnFrom) {
        Objects.requireNonNull(schachfigurAnFrom);
        // return (schachfigurAnFrom.color.ordinal() != zugHistorie.size() % 2);
        return true;
    }


    private boolean existiertSpielfigurAnStartposition(Spielfigur schachfigurAnFrom) {
        return !(schachfigurAnFrom == null);
    }


}
