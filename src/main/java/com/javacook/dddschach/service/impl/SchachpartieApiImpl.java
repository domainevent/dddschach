package com.javacook.dddschach.service.impl;

import com.javacook.dddschach.domain.*;
import com.javacook.dddschach.service.api.SchachpartieApi;
import com.javacook.dddschach.service.api.UngueltigeSpielIdException;
import com.javacook.dddschach.service.api.UngueltigerHalbzugException;
import org.apache.log4j.Logger;

import java.io.IOException;
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
    public NeuesSpielResponse neuesSpiel(NeuesSpielRequest request)  {
        try {
            final Schachpartie$ schachpartie = SCHACHPARTIE_FACTORY.createSchachpartie();
            SCHACHPARTIE_REPOSITORY.save(schachpartie);
            final SpielId$ spielId = new SpielId$(schachpartie.getId().getId());
            return new NeuesSpielResponse(spielId);
        }
        catch (IOException e) {
            LOG.error(e);
            throw new RuntimeException(e);
        }
    }


    @Override
    public FuehreHalbzugAusResponse fuehreHalbzugAus(FuehreHalbzugAusRequest request)
            throws UngueltigerHalbzugException, UngueltigeSpielIdException {

        LOG.trace("Ausfuehren des Halbzuges: " + request);

        final SpielId$ spielId = new SpielId$(request.getSpielId());
        final Halbzug$ halbzug = new Halbzug$(request.getHalbzug());

        try {
            final Optional<Schachpartie$> schachpartie = SCHACHPARTIE_REPOSITORY.findById(spielId);
            if (!schachpartie.isPresent()) {
                throw new UngueltigeSpielIdException(spielId);
            }
            final int zugIndex = schachpartie.get().fuehreHalbzugAus(halbzug);
            SCHACHPARTIE_REPOSITORY.save(schachpartie.get());
            final FuehreHalbzugAusResponse response = new FuehreHalbzugAusResponse(zugIndex);
            LOG.trace("Ausfuehren des Halbzuges: " + response);
            return response;
        }
        catch (UngueltigeSpielIdException e) {
            LOG.warn("Die Spiel-ID '" + spielId + "' ist ungueltig.");
            throw e;
        }
        catch (UngueltigerHalbzugException e) {
            LOG.debug("Der Halbzug " + request.getHalbzug() + " ist ungueltig.");
            throw e;
        }
        catch (IOException e) {
            LOG.error(e);
            throw new RuntimeException(e);
        }
    }


    @Override
    public AktuellesSpielbrettResponse aktuellesSpielbrett(AktuellesSpielbrettRequest request)
            throws UngueltigeSpielIdException
    {
        LOG.trace("Abfrage des Spielfeldes: " + request);
        final SpielId$ spielId = new SpielId$(request.getSpielId());
        try {
            final Optional<Schachpartie$> schachpartie = SCHACHPARTIE_REPOSITORY.findById(spielId);
            if (!schachpartie.isPresent()) {
                throw new UngueltigeSpielIdException(spielId);
            }
            final Spielbrett$ spielbrettExt = new Spielbrett$(schachpartie.get().getSpielbrett());
            final AktuellesSpielbrettResponse response = new AktuellesSpielbrettResponse(spielbrettExt);
            LOG.trace("Abfrage des Spielfeldes: " + response);
            return response;
        }
        catch (UngueltigeSpielIdException e) {
            LOG.warn("Die Spiel-ID '" + spielId + "' ist ungueltig.");
            throw e;
        }
        catch (IOException e) {
            LOG.error("Das Spielbrett mit Id " + request.getSpielId() + " konnte nicht geladen werden.");
            throw new RuntimeException(e);
        }
    }


}
