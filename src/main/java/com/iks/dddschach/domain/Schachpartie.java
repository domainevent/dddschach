package com.iks.dddschach.domain;

import com.iks.dddschach.domain.base.EntityIdObject;

import static com.iks.dddschach.api.SchachpartieApiImpl.UngueltigerHalbzugException;


/**
 * Created by vollmer on 18.05.17.
 */
public class Schachpartie extends EntityIdObject<SpielId> {

    final static HalbzugValidation VALIDATION = new HalbzugValidation();
    final HalbzugHistorie halbzugHistorie = new HalbzugHistorie();
    private Spielbrett spielbrett;


    public Schachpartie(SpielId id) {
        super(id);
        spielbrett = SpielbrettFactory.createInitialesSpielbrett();
    }


    public int fuehreHalbzugAus(Halbzug halbzug) throws UngueltigerHalbzugException {
        if (!VALIDATION.validiere(halbzug, halbzugHistorie, spielbrett).valid) {
            throw new UngueltigerHalbzugException(halbzug);
        }
        spielbrett = new Spielbrett(spielbrett) {{
            final Spielfigur spielfigurFrom = getSchachfigurAnPosition(halbzug.from);
            setSchachfigurAnPosition(halbzug.from, null);
            setSchachfigurAnPosition(halbzug.to, spielfigurFrom);
        }};
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
