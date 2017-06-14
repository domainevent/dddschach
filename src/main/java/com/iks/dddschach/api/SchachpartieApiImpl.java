package com.iks.dddschach.api;

import com.iks.dddschach.domain.*;
import java.text.ParseException;
import java.util.Optional;


/**
 * Created by javacook on 21.04.17.
 */
public class SchachpartieApiImpl implements SchachpartieApi {

    public final static SchachpartieFactory SCHACHPARTIE_FACTORY = new SchachpartieFactory();
    private final SchachpartieRepository SCHACHPARTIE_REPOSITORY;

    public SchachpartieApiImpl(SchachpartieRepository schachpartieRepository) {
        SCHACHPARTIE_REPOSITORY = schachpartieRepository;
    }

    @Override
    public SpielId neuesSpiel(Optional<String> vermerk) {
        final Schachpartie schachpartie = SCHACHPARTIE_FACTORY.createSchachpartie();
        SCHACHPARTIE_REPOSITORY.save(schachpartie);
        return schachpartie.getId();
    }


    @Override
    public Halbzug parse(String eingabe) throws ParseException {
        return SpielNotationParser.parse(eingabe);
    }


    @Override
    public int fuehreHalbzugAus(SpielId spielId, Halbzug halbzug)
            throws UngueltigerHalbzugException, UngueltigeSpielIdException {

        final Optional<Schachpartie> schachpartie = SCHACHPARTIE_REPOSITORY.findById(spielId);
        if (!schachpartie.isPresent()) {
            throw new UngueltigeSpielIdException(spielId);
        }
        return schachpartie.get().fuehreHalbzugAus(halbzug);
    }


    @Override
    public Spielbrett aktuellesSpielbrett(SpielId spielId) throws UngueltigeSpielIdException {
        final Optional<Schachpartie> schachpartie = SCHACHPARTIE_REPOSITORY.findById(spielId);
        if (!schachpartie.isPresent()) {
            throw new UngueltigeSpielIdException(spielId);
        }
        return schachpartie.get().aktuellesSpielbrett();
    }

}
