package com.iks.dddschach.api;

import com.iks.dddschach.domain.*;

import java.text.ParseException;
import java.util.Optional;


/**
 * Implementierung des Interface <code>SchachpartieApi</code>
 */
public class SchachpartieApiImpl implements SchachpartieApi {

    @Override
    public SpielId neuesSpiel(Optional<String> vermerk) throws Exception {
        // TODO: Zu implementieren
        return new SpielId();
    }


    @Override
    public Halbzug parse(String eingabe) throws ParseException {
        return new Halbzug(eingabe);
    }


    @Override
    public int fuehreHalbzugAus(SpielId spielId, Halbzug halbzug)
            throws UngueltigerHalbzugException, UngueltigeSpielIdException {
        // TODO: Zu implementieren
        return -1;
    }


    @Override
    public Spielbrett aktuellesSpielbrett(SpielId spielId) throws UngueltigeSpielIdException {
        // TODO: Zu implementieren
        return SpielbrettFactory.createInitialesSpielbrett();
    }

}
