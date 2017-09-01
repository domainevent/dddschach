package com.iks.dddschach.service.impl;

import com.iks.dddschach.domain.*;
import com.iks.dddschach.rest.RestServiceNew;
import com.iks.dddschach.service.api.SchachpartieApi;
import org.apache.log4j.Logger;

import javax.ws.rs.InternalServerErrorException;
import java.io.IOException;
import java.text.ParseException;
import java.util.Optional;


/**
 * Implementierung der Schnittstelle <code>SchachpartieApi</code>
 */
public class SchachpartieApiImpl implements SchachpartieApi {

    final static Logger LOG = Logger.getLogger(SchachpartieApiImpl.class);
    public final static SchachpartieFactory SCHACHPARTIE_FACTORY = new SchachpartieFactory();
    private final SchachpartieRepository SCHACHPARTIE_REPOSITORY;

    public SchachpartieApiImpl(SchachpartieRepository schachpartieRepository) {
        SCHACHPARTIE_REPOSITORY = schachpartieRepository;
    }


    @Override
    public NeuesSpielResponse neuesSpiel(NeuesSpielRequest request) throws Exception {
        final Schachpartie$ schachpartie = SCHACHPARTIE_FACTORY.createSchachpartie();
        SCHACHPARTIE_REPOSITORY.save(schachpartie);
        final SpielId$ spielId = new SpielId$(schachpartie.getId().getId());
        return new NeuesSpielResponse(spielId);
    }

    @Override
    public Halbzug$ parse(String eingabe) throws ParseException {
        return SpielNotationParser.parse(eingabe);
    }




    @Override
    public FuehreHalbzugAusResponse fuehreHalbzugAus(FuehreHalbzugAusRequest request)
            throws UngueltigerHalbzugException, UngueltigeSpielIdException, IOException {

        final SpielId$ spielId = new SpielId$(request.getSpielId());
        final Halbzug$ halbzug = new Halbzug$(request.getHalbzug());

        final Optional<Schachpartie$> schachpartie =
                SCHACHPARTIE_REPOSITORY.findById(spielId);

        if (!schachpartie.isPresent()) {
            LOG.warn("Die Spiel-ID '" + spielId + "' ist ungueltig.");
            throw new UngueltigeSpielIdException(spielId);
        }
        try {
            final int no = schachpartie.get().fuehreHalbzugAus(halbzug);
            SCHACHPARTIE_REPOSITORY.save(schachpartie.get());
            return new FuehreHalbzugAusResponse(no);
        }
        catch (UngueltigerHalbzugException e) {
            LOG.debug("Der Halbzug " + request.getHalbzug() + " ist ungueltig.");
            throw e;
        }
        catch (IOException e) {
            LOG.debug("Der Halbzug " + request.getHalbzug() + " konnte nicht persistiert werden.");
            throw e;
        }
    }


    @Override
    public AktuellesSpielbrettResponse aktuellesSpielbrett(AktuellesSpielbrettRequest request) throws UngueltigeSpielIdException, IOException {
        final SpielId$ spielId = (SpielId$)request.getSpielId();
        final Optional<Schachpartie$> schachpartie = SCHACHPARTIE_REPOSITORY.findById(spielId);
        if (!schachpartie.isPresent()) {
            throw new UngueltigeSpielIdException(spielId);
        }
        final Spielbrett$ spielbrettExt = new Spielbrett$(schachpartie.get().getSpielbrett());
        return new AktuellesSpielbrettResponse(spielbrettExt);
    }


}
