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
public class GesamtValidator implements HalbzugValidation, DomainService {

    final static RochadenCheck ROCHADEN_CHECK = new RochadenCheck();
    final static BauernumwandlungCheck BAUERNUMW_CHECK = new BauernumwandlungCheck();
    final static ErreicheZielCheck ERREICHE_ZIEL_CHECK = new ErreicheZielCheck();
    final static SchlagRegel SCHLAG_REGEL = new SchlagRegel();
    final static SchachCheck SCHACH_CHECK = new SchachCheck();

    public ValidationResult validiere(
            Halbzug halbzug,
            List<Halbzug> zugHistorie,
            Spielbrett spielbrett) {


        final Spielfigur zugFigur = spielbrett.getSchachfigurAnPosition(halbzug.from);
        if (zugFigur == null) {
            return new ValidationResult(Zugregel.STARTFELD_MUSS_SPIELFIGUR_ENTHALTEN);
        }

        if (!istRichtigerSpielerAmZug(zugFigur, zugHistorie)) {
            return new ValidationResult(false, DER_RICHTIGE_SPIELER_MUSS_AM_ZUG_SEIN);
        }

        final ValidationResult rochadenCheckResult = ROCHADEN_CHECK.validiere(halbzug, zugHistorie, spielbrett);
        if (rochadenCheckResult.gueltig || istRouchadenZugAberUnzulaessig(rochadenCheckResult)) {
            return rochadenCheckResult;
        }

        final ValidationResult bauernumwCheckResult = BAUERNUMW_CHECK.validiere(halbzug, zugHistorie, spielbrett);
        if (bauernumwCheckResult.gueltig) {
            return bauernumwCheckResult;
        }

        ValidationResult zielErreichbarResult = ERREICHE_ZIEL_CHECK.validiere(halbzug, zugHistorie, spielbrett);
        if (!zielErreichbarResult.gueltig) return zielErreichbarResult;

        ValidationResult schlagRegelResult = SCHLAG_REGEL.validiere(halbzug, zugHistorie, spielbrett);
        if (!schlagRegelResult.gueltig) return schlagRegelResult;

        ValidationResult schachCheckResult = SCHACH_CHECK.validiere(halbzug, zugHistorie, spielbrett);
        if (!schachCheckResult.gueltig) return schachCheckResult;

        return new ValidationResult();
    }


    private boolean istRouchadenZugAberUnzulaessig(ValidationResult rochadenCheckResult) {
        return !rochadenCheckResult.gueltig &&
                rochadenCheckResult.verletzteZugregel != Zugregel.HALBZUG_IST_KEIN_ROCHADE;
    }


    private boolean istRichtigerSpielerAmZug(Spielfigur schachfigurAnFrom, List<Halbzug> zugHistorie) {
        Objects.requireNonNull(schachfigurAnFrom);
        return (schachfigurAnFrom.color.ordinal() == zugHistorie.size() % 2);
        // return true;
    }


}
