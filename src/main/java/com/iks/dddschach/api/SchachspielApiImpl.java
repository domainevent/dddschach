package com.iks.dddschach.api;

import com.iks.dddschach.domain.Schachbrett;
import com.iks.dddschach.domain.SpielId;
import com.iks.dddschach.domain.Halbzug;


/**
 * Created by javacook on 21.04.17.
 */
public class SchachspielApiImpl implements SchachspielApi {

    @Override
    public SpielId neuesSpiel() {
        // TODO: Zu implementieren
        return new SpielId("spielIdDummy");
    }


    @Override
    public int fuehreHalbzugAus(SpielId spielId, Halbzug zug) {
        // TODO: Zu implementieren
        return -1;
    }


    @Override
    public Schachbrett schachBrett(SpielId gameId) {
        // TODO: Zu implementieren
        return new Schachbrett();
    }

}
