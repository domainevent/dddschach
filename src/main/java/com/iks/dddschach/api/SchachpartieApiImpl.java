package com.iks.dddschach.api;

import com.iks.dddschach.domain.*;

import java.util.Optional;


/**
 * Created by javacook on 21.04.17.
 */
public class SchachpartieApiImpl implements SchachpartieApi {

    public final static SchachpartieFactory SCHACHPARTIE_FACTORY = new SchachpartieFactory();
    public final static SchachpartieRepository SCHACHPARTIE_REPOSITORY = new SchachpartieRepository();


    @Override
    public SpielId neuesSpiel(Optional<String> vermerk) {
        final Schachpartie schachpartie = SCHACHPARTIE_FACTORY.createSchachpartie();
        SCHACHPARTIE_REPOSITORY.save(schachpartie);
        return schachpartie.getId();
    }


    @Override
    public int fuehreHalbzugAus(SpielId spielId, Halbzug halbzug) throws UngueltigerHalbzugException {
        final Schachpartie schachpartie = SCHACHPARTIE_REPOSITORY.findById(spielId);
        return schachpartie.fuehreHalbzugAus(halbzug);
    }


    @Override
    public Spielbrett spielbrett(SpielId spielId) {
        final Schachpartie schachpartie = SCHACHPARTIE_REPOSITORY.findById(spielId);
        return schachpartie.aktuellesSpielbrett();
    }

}
