package com.iks.dddschach.api;

import com.iks.dddschach.domain.*;

import java.util.Optional;


/**
 * Created by javacook on 21.04.17.
 */
public class SchachpartieApiImpl implements SchachpartieApi {

    @Override
    public SpielId neuesSpiel(Optional<String> vermerk) throws Exception {
        // TODO: Zu implementieren
        return new SpielId();
    }


    @Override
    public int fuehreHalbzugAus(SpielId spielId, Halbzug halbzug) throws UngueltigerHalbzugException {
        // TODO: Zu implementieren
        return -1;
    }


    @Override
    public Spielbrett spielbrett(SpielId gameId) throws Exception {
        // TODO: Zu implementieren
        return SpielbrettFactory.createInitialesSpielbrett();
    }

}
