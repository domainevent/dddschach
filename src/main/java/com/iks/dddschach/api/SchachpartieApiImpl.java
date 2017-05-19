package com.iks.dddschach.api;

import com.iks.dddschach.domain.*;

import java.util.Optional;


/**
 * Created by javacook on 21.04.17.
 */
public class SchachpartieApiImpl implements SchachpartieApi {

    @Override
    public SpielId neuesSpiel(Optional<String> vermerk) {
        // TODO: Zu implementieren
        return new SpielId();
    }


    public static class UngueltigerHalbzugException extends Exception {
        public final Halbzug halbzug;

        public UngueltigerHalbzugException(Halbzug halbzug) {
            this.halbzug = halbzug;
        }
    }

    @Override
    public int fuehreHalbzugAus(SpielId spielId, Halbzug halbzug) {
        // TODO: Zu implementieren
        return -1;
    }


    @Override
    public Spielbrett spielbrett(SpielId gameId) {
        // TODO: Zu implementieren
        return SpielbrettFactory.createInitialesSpielbrett();
    }

}
