package com.iks.dddschach.service.impl;

import com.iks.dddschach.domain.*;
import com.iks.dddschach.olddomain.*;
import com.iks.dddschach.service.api.SchachpartieApi;

import java.io.IOException;
import java.text.ParseException;
import java.util.Optional;


/**
 * Implementierung der Schnittstelle <code>SchachpartieApi</code>
 */
public class SchachpartieApiImpl implements SchachpartieApi {

    public final static SchachpartieFactory SCHACHPARTIE_FACTORY = new SchachpartieFactory();
    private final SchachpartieRepository SCHACHPARTIE_REPOSITORY;

    public SchachpartieApiImpl(SchachpartieRepository schachpartieRepository) {
        SCHACHPARTIE_REPOSITORY = schachpartieRepository;
    }


    @Override
    public NeuesSpielResponse neuesSpiel(NeuesSpielRequest request) throws Exception {
        final SchachpartieExt schachpartie = SCHACHPARTIE_FACTORY.createSchachpartie();
        SCHACHPARTIE_REPOSITORY.save(schachpartie);
        final SpielIdExt spielId = new SpielIdExt(schachpartie.getSpielId().getId());
        return new NeuesSpielResponse(spielId);
    }

    @Override
    public HalbzugExt parse(String eingabe) throws ParseException {
        return SpielNotationParser.parse(eingabe);
    }




    @Override
    public FuehreHalbzugAusResponse fuehreHalbzugAus(FuehreHalbzugAusRequest request)
            throws UngueltigerHalbzugException, UngueltigeSpielIdException, IOException {

        final SpielId spielId = request.getSpielId();
        final Halbzug halbzug = request.getHalbzug();

        final Optional<SchachpartieExt> schachpartie =
                SCHACHPARTIE_REPOSITORY.findById(spielId);
        if (!schachpartie.isPresent()) {
            throw new UngueltigeSpielIdException(spielId);
        }
        final int no = schachpartie.get().fuehreHalbzugAus(halbzug);
        SCHACHPARTIE_REPOSITORY.save(schachpartie.get());
        return new FuehreHalbzugAusResponse(no);
    }


    @Override
    public AktuellesSpielbrettResponse aktuellesSpielbrett(AktuellesSpielbrettRequest request) throws UngueltigeSpielIdException, IOException {
        final SpielId spielId = request.getSpielId();
        final Optional<SchachpartieExt> schachpartie = SCHACHPARTIE_REPOSITORY.findById(spielId);
        if (!schachpartie.isPresent()) {
            throw new UngueltigeSpielIdException(spielId);
        }
        final SpielbrettExt spielbrettExt = new SpielbrettExt(schachpartie.get().aktuellesSpielbrett());
        return new AktuellesSpielbrettResponse(spielbrettExt);
    }


}
