package com.iks.dddschach.domain.validation;

import com.iks.dddschach.domain.*;
import com.iks.dddschach.domain.base.DomainService;
import org.apache.commons.lang3.Validate;

import java.util.List;
import java.util.Objects;

import static com.iks.dddschach.domain.validation.Zugregel.DER_RICHTIGE_SPIELER_MUSS_AM_ZUG_SEIN;


/**
 * Start der Gesamt-Validation. Von hier aus werden alle weiteren Validationen gestartet und verarbeitet.
 */
public class GesamtValidator implements HalbzugValidation, DomainService {

    final static RochadenCheck ROCHADEN_CHECK = new RochadenCheck();
    final static BauernumwCheck BAUERNUMW_CHECK = new BauernumwCheck();
    final static EnPassantCheck ENPASSANT_CHECK = new EnPassantCheck();
    final static ErreicheZielCheck ERREICHE_ZIEL_CHECK = new ErreicheZielCheck();
    final static SchlagRegel SCHLAG_REGEL = new SchlagRegel();
    final static SchachCheck SCHACH_CHECK = new SchachCheck();

    public ValidationResult validiere(
            Halbzug$ halbzug,
            List<? extends Halbzug> halbzugHistorie,
            Spielbrett$ spielbrett) {

        Validate.isTrue(halbzug != null, "Argument halbzug is null.");
        Validate.isTrue(halbzugHistorie != null, "Argument halbzugHistorie is null.");
        Validate.isTrue(spielbrett != null, "Argument spielbrett is null.");

        final Spielfigur$ zugFigur = spielbrett.getSchachfigurAnPosition(halbzug.getVon());
        if (zugFigur == null) {
            return new ValidationResult(Zugregel.STARTFELD_MUSS_SPIELFIGUR_ENTHALTEN);
        }

        if (!istRichtigerSpielerAmZug(zugFigur, halbzugHistorie)) {
            return new ValidationResult(false, DER_RICHTIGE_SPIELER_MUSS_AM_ZUG_SEIN);
        }

        // Überprüft, ob ich versuche mich selbst oder einen König zu schlagen:
        ValidationResult schlagRegelResult = SCHLAG_REGEL.validiere(halbzug, halbzugHistorie, spielbrett);
        if (!schlagRegelResult.gueltig) {
            return schlagRegelResult;
        }

        // Die Schach-Regel muss vor den Sonderregeln Rochade, EnPassant und Bauernumwandlung
        // erfolgen, die sie sonst ihre Züge zuließen, obwohl man im Schach stünde.
        //
        ValidationResult schachCheckResult = SCHACH_CHECK.validiere(halbzug, halbzugHistorie, spielbrett);
        if (!schachCheckResult.gueltig) {
            return schachCheckResult;
        }

        final ValidationResult rochadenCheckResult = ROCHADEN_CHECK.validiere(halbzug, halbzugHistorie, spielbrett);
        if (rochadenCheckResult.gueltig || istRouchadenZugAberUnzulaessig(rochadenCheckResult)) {
            return rochadenCheckResult;
        }

        final ValidationResult enPassantCheckResult = ENPASSANT_CHECK.validiere(halbzug, halbzugHistorie, spielbrett);
        if (enPassantCheckResult.gueltig || istEnPassantZugAberUnzulaessig(enPassantCheckResult)) {
            return enPassantCheckResult;
        }

        final ValidationResult bauernumwCheckResult = BAUERNUMW_CHECK.validiere(halbzug, halbzugHistorie, spielbrett);
        if (bauernumwCheckResult.gueltig) {
            return bauernumwCheckResult;
        }

        // Die folgende Regel muss nach den Sonderregeln Rochade und En-Passant erfolgen, da
        // letztere sonst von dieser Regel abgelehnt würden:
        //
        ValidationResult zielErreichbarResult = ERREICHE_ZIEL_CHECK.validiere(halbzug, halbzugHistorie, spielbrett);
        if (!zielErreichbarResult.gueltig) {
            return zielErreichbarResult;
        }

        return new ValidationResult();
    }


    private boolean istEnPassantZugAberUnzulaessig(ValidationResult enPassantCheckResult) {
        return !enPassantCheckResult.gueltig &&
                enPassantCheckResult.verletzteZugregel != Zugregel.HALBZUG_IST_KEIN_EN_PASSANT;
    }

    private boolean istRouchadenZugAberUnzulaessig(ValidationResult rochadenCheckResult) {
        return !rochadenCheckResult.gueltig &&
                rochadenCheckResult.verletzteZugregel != Zugregel.HALBZUG_IST_KEINE_ROCHADE;
    }


    private boolean istRichtigerSpielerAmZug(Spielfigur schachfigurAnFrom, List<? extends Halbzug> zugHistorie) {
        Objects.requireNonNull(schachfigurAnFrom);
        return (schachfigurAnFrom.getFarbe().ordinal() == zugHistorie.size() % 2);
        // return true;
    }


}
