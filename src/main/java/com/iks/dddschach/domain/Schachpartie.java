package com.iks.dddschach.domain;

import com.iks.dddschach.domain.base.EntityObject;
import com.iks.dddschach.domain.base.EnumObject;


/**
 * Created by vollmer on 18.05.17.
 */
public class Schachpartie extends EntityObject<SpielId> {

    Spielbrett spielbrett;
    HalbzugHistorie halbzugHistorie = new HalbzugHistorie();

    public Schachpartie(SpielId id) {
        super(id);
    }

    public int fuehreHalbzugAus(Halbzug halbzug) {
        new Spielbrett(spielbrett) {{
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
