package com.iks.dddschach.api;

import com.iks.dddschach.domain.*;

import java.text.ParseException;
import java.util.Optional;


/**
 * Implementierung der Schnittstelle <code>SchachpartieApi</code>
 */
public class SchachpartieApiImpl implements SchachpartieApi {

    @Override
    public SpielId neuesSpiel(Optional<String> vermerk) throws Exception {
        // TODO: Zu implementieren
        return null;
    }


    @Override
    public Halbzug parse(String eingabe) throws ParseException {
        return SpielNotationParser.parse(eingabe);
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
