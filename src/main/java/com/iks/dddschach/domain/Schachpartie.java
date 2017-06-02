package com.iks.dddschach.domain;

import com.iks.dddschach.api.SchachpartieApi.UngueltigerHalbzugException;
import com.iks.dddschach.domain.base.EntityIdObject;
import com.iks.dddschach.domain.validation.HalbzugValidation;
import com.iks.dddschach.domain.validation.HalbzugValidator;


/**
 * Created by vollmer on 18.05.17.
 */
public class Schachpartie extends EntityIdObject<SpielId> {

    final static HalbzugValidation VALIDATION = new HalbzugValidator();
    final HalbzugHistorie halbzugHistorie = new HalbzugHistorie();
    private Spielbrett spielbrett;


    public Schachpartie(SpielId id) {
        super(id);
        spielbrett = SpielbrettFactory.createInitialesSpielbrett();
    }


    public int fuehreHalbzugAus(Halbzug halbzug) throws UngueltigerHalbzugException {
        if (!VALIDATION.validiere(halbzug, halbzugHistorie.halbzuege, spielbrett).gueltig) {
            throw new UngueltigerHalbzugException(halbzug);
        }
        spielbrett = spielbrett.wendeHalbzugAn(halbzug);
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
