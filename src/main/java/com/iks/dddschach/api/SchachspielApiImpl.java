package com.iks.dddschach.api;

import com.iks.dddschach.domain.Spielbrett;
import com.iks.dddschach.domain.SpielId;
import com.iks.dddschach.domain.Halbzug;
import com.iks.dddschach.util.SampleDataFactory;

import java.util.Optional;


/**
 * Created by javacook on 21.04.17.
 */
public class SchachspielApiImpl implements SchachspielApi {

    @Override
    public SpielId neuesSpiel(Optional<String> vermerk) {
        // TODO: Zu implementieren
        return new SpielId();
    }


    @Override
    public int fuehreHalbzugAus(SpielId spielId, Halbzug halbzug) {
        // TODO: Zu implementieren
        return -1;
    }


    @Override
    public Spielbrett schachBrett(SpielId gameId) {
        // TODO: Zu implementieren
        return SampleDataFactory.createInitialesSchachbrett();
    }

}
