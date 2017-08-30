package com.iks.dddschach.domain;

import com.iks.dddschach.domain.base.EntityIdObject;
import com.iks.dddschach.domain.validation.*;
import com.iks.dddschach.service.api.SchachpartieApi.UngueltigerHalbzugException;

import java.util.ArrayList;

import static com.iks.dddschach.domain.validation.Zugregel.DIE_PARTIE_ENDET_MATT;
import static com.iks.dddschach.domain.validation.Zugregel.DIE_PARTIE_ENDET_PATT;


public class Schachpartie$ extends Schachpartie implements EntityIdObject<SpielId> {

    final static HalbzugValidation VALIDATOR = new GesamtValidator();
    final static PattMattCheck PATT_MATT_CHECK = new PattMattCheck();

    public Schachpartie$() {
        super();
    }

    public Schachpartie$(SpielId$ spielId) {
        super(spielId, new HalbzugHistorie$(new ArrayList<>()), SpielbrettFactory.createInitialesSpielbrett());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityIdObject<?> that = (EntityIdObject<?>) o;
        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }


    /**
     * Führt den Halbzug auf dem Brett unter Berücksichtigung alle Schachregeln aus. Hält der
     * Halbzug einer Überprüfung nicht stand, wird er nicht ausgeführt und anstatt dessen eine
     * Exception ausgelöst
     * @param halbzug auszuführender {@link Halbzug}
     * @return Anzahl der bislang ausgeführten Züge
     * @throws UngueltigerHalbzugException, falls der Halbzug nicht regelkonform ist
     */
    public int fuehreHalbzugAus(Halbzug$ halbzug) throws UngueltigerHalbzugException {
        final HalbzugValidation.ValidationResult validationResult =
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
        if (validationResult instanceof RochadenCheck.RochadenCheckResult) {
            final RochadenCheck.RochadenCheckResult rochadenCheckResult = (RochadenCheck.RochadenCheckResult) validationResult;
            spielbrett = spielbrett.wendeHalbzugAn(rochadenCheckResult.turmHalbZug);
        }

        // bei einem En-Passant-Zug muss der geschlagene Bauer noch entfernt werden:
        if (validationResult instanceof EnPassantCheck.EnPassantCheckResult) {
            final EnPassantCheck.EnPassantCheckResult enPassantCheckResult = (EnPassantCheck.EnPassantCheckResult) validationResult;
            spielbrett = new Spielbrett$(spielbrett) {{
                setSchachfigurAnPosition(enPassantCheckResult.positionGeschlBauer, null);
            }};
        }

        // Bei einer Bauernumwandlung muss der Bauer anschließend noch gegen die Zielfigur eingetauscht werden:
        if (validationResult instanceof BauernumwCheck.BauernumwCheckResult) {
            final BauernumwCheck.BauernumwCheckResult bauernumwCheckResult = (BauernumwCheck.BauernumwCheckResult) validationResult;
            spielbrett = new Spielbrett$(spielbrett) {{
                setSchachfigurAnPosition(halbzug.getNach(), bauernumwCheckResult.zielFigur);
            }};
        }

        halbzugHistorie.withHalbzuege(halbzug);
        return halbzugHistorie.halbzuege.size();
    }


    /**
     * Liefert das aktuelle Spielbrett
     * @return aktuelles {@link Spielbrett}
     */
    public Spielbrett$ aktuellesSpielbrett() {
        return spielbrett;
    }


    /**
     * Liefert die Historie alle bislang ausgeführten Halbzüge
     * @return {@link HalbzugHistorie}
     */
    public HalbzugHistorie$ halbzugHistorie() {
        return halbzugHistorie;
    }

}