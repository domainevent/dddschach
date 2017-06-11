package com.iks.dddschach.domain;

import com.iks.dddschach.api.SchachpartieApi.UngueltigerHalbzugException;
import com.iks.dddschach.domain.base.EntityIdObject;
import com.iks.dddschach.domain.validation.BauernumwCheck.BauernumwCheckResult;
import com.iks.dddschach.domain.validation.EnPassantCheck.EnPassantCheckResult;
import com.iks.dddschach.domain.validation.HalbzugValidation;
import com.iks.dddschach.domain.validation.HalbzugValidation.ValidationResult;
import com.iks.dddschach.domain.validation.GesamtValidator;
import com.iks.dddschach.domain.validation.RochadenCheck.RochadenCheckResult;


/**
 * Created by vollmer on 18.05.17.
 */
public class Schachpartie extends EntityIdObject<SpielId> {

    final static HalbzugValidation VALIDATION = new GesamtValidator();
    final HalbzugHistorie halbzugHistorie = new HalbzugHistorie();
    private Spielbrett spielbrett;


    public Schachpartie(SpielId id) {
        super(id);
        spielbrett = SpielbrettFactory.createInitialesSpielbrett();
    }


    public int fuehreHalbzugAus(Halbzug halbzug) throws UngueltigerHalbzugException {
        final ValidationResult validationResult =
                VALIDATION.validiere(halbzug, halbzugHistorie.halbzuege, spielbrett);

        if (!validationResult.gueltig) {
            throw new UngueltigerHalbzugException(halbzug, validationResult.verletzteZugregel);
        }
        spielbrett = spielbrett.wendeHalbzugAn(halbzug);

        if (validationResult instanceof RochadenCheckResult) {
            final RochadenCheckResult rochadenCheckResult = (RochadenCheckResult) validationResult;
            spielbrett = spielbrett.wendeHalbzugAn(rochadenCheckResult.turmHalbZug);
        }

        if (validationResult instanceof EnPassantCheckResult) {
            final EnPassantCheckResult enPassantCheckResult = (EnPassantCheckResult) validationResult;
            spielbrett = new Spielbrett(spielbrett) {{
                setSchachfigurAnPosition(enPassantCheckResult.positionGeschlBauer, null);
            }};
        }

        if (validationResult instanceof BauernumwCheckResult) {
            final BauernumwCheckResult bauernumwCheckResult = (BauernumwCheckResult) validationResult;
            spielbrett = new Spielbrett(spielbrett) {{
                setSchachfigurAnPosition(halbzug.to, bauernumwCheckResult.zielFigur);
            }};
        }

        halbzugHistorie.addHalbzug(halbzug);
        return halbzugHistorie.size();
    }


    public Spielbrett aktuellesSpielbrett() {
        return spielbrett;
    }

    public HalbzugHistorie spielzuege() {
        return halbzugHistorie;
    }

}
