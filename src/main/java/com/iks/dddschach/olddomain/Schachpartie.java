package com.iks.dddschach.olddomain;

import com.iks.dddschach.service.api.SchachpartieApi.UngueltigerHalbzugException;
import com.iks.dddschach.domain.base.EntityIdObject;
import com.iks.dddschach.domain.validation.BauernumwCheck.BauernumwCheckResult;
import com.iks.dddschach.domain.validation.EnPassantCheck.EnPassantCheckResult;
import com.iks.dddschach.domain.validation.HalbzugValidation;
import com.iks.dddschach.domain.validation.HalbzugValidation.ValidationResult;
import com.iks.dddschach.domain.validation.GesamtValidator;
import com.iks.dddschach.domain.validation.PattMattCheck;
import com.iks.dddschach.domain.validation.RochadenCheck.RochadenCheckResult;

import static com.iks.dddschach.domain.validation.Zugregel.DIE_PARTIE_ENDET_MATT;
import static com.iks.dddschach.domain.validation.Zugregel.DIE_PARTIE_ENDET_PATT;


/**
 * Das zentrale Aggregat, über das die Anwendung bedient wird.
 * @author javacook
 */
public class Schachpartie extends EntityIdObject<SpielId> {

    final static HalbzugValidation VALIDATOR = new GesamtValidator();
    final static PattMattCheck PATT_MATT_CHECK = new PattMattCheck();
    protected HalbzugHistorie halbzugHistorie = new HalbzugHistorie();
    protected Spielbrett spielbrett;


    /**
     * Konstruktor
     * @param id (weltweit) eindeutige ID der Partie
     */
    public Schachpartie(SpielId id) {
        super(id);
        spielbrett = SpielbrettFactory.createInitialesSpielbrett();
    }


    /**
     * Führt den Halbzug auf dem Brett unter Berücksichtigung alle Schachregeln aus. Hält der
     * Halbzug einer Überprüfung nicht stand, wird er nicht ausgeführt und anstatt dessen eine
     * Exception ausgelöst
     * @param halbzug auszuführender {@link Halbzug}
     * @return Anzahl der bislang ausgeführten Züge
     * @throws UngueltigerHalbzugException, falls der Halbzug nicht regelkonform ist
     */
    public int fuehreHalbzugAus(Halbzug halbzug) throws UngueltigerHalbzugException {
        final ValidationResult validationResult =
                VALIDATOR.validiere(halbzug, halbzugHistorie.halbzuege, spielbrett);
        if (!validationResult.gueltig) {
            final PattMattCheck.PattMatt pattMatt = PATT_MATT_CHECK.analysiere(halbzugHistorie.halbzuege, spielbrett);
            switch (pattMatt) {
                case MATT: throw new UngueltigerHalbzugException(halbzug, DIE_PARTIE_ENDET_MATT);
                case PATT: throw new UngueltigerHalbzugException(halbzug, DIE_PARTIE_ENDET_PATT);
            }
            throw new UngueltigerHalbzugException(halbzug, validationResult.verletzteZugregel);
        }
        spielbrett = spielbrett.wendeHalbzugAn(halbzug);

        // Bei einer Rochade muss der Turm zusätzlich noch gezogen werden:
        if (validationResult instanceof RochadenCheckResult) {
            final RochadenCheckResult rochadenCheckResult = (RochadenCheckResult) validationResult;
            spielbrett = spielbrett.wendeHalbzugAn(rochadenCheckResult.turmHalbZug);
        }

        // bei einem En-Passant-Zug muss der geschlagene Bauer noch entfernt werden:
        if (validationResult instanceof EnPassantCheckResult) {
            final EnPassantCheckResult enPassantCheckResult = (EnPassantCheckResult) validationResult;
            spielbrett = new Spielbrett(spielbrett) {{
                setSchachfigurAnPosition(enPassantCheckResult.positionGeschlBauer, null);
            }};
        }

        // Bei einer Bauernumwandlung muss der Bauer anschließend noch gegen die Zielfigur eingetauscht werden:
        if (validationResult instanceof BauernumwCheckResult) {
            final BauernumwCheckResult bauernumwCheckResult = (BauernumwCheckResult) validationResult;
            spielbrett = new Spielbrett(spielbrett) {{
                setSchachfigurAnPosition(halbzug.to, bauernumwCheckResult.zielFigur);
            }};
        }

        halbzugHistorie.addHalbzug(halbzug);
        return halbzugHistorie.size();
    }


    /**
     * Liefert das aktuelle Spielbrett
     * @return aktuelles {@link Spielbrett}
     */
    public Spielbrett aktuellesSpielbrett() {
        return spielbrett;
    }


    /**
     * Liefert die Historie alle bislang ausgeführten Halbzüge
     * @return {@link HalbzugHistorie}
     */
    public HalbzugHistorie halbzugHistorie() {
        return halbzugHistorie;
    }

}