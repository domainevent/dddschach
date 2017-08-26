package com.iks.dddschach.api;

import com.iks.dddschach.domain.*;
import com.iks.dddschach.olddomain.*;
import com.iks.dddschach.olddomain.Position;
import com.iks.dddschach.olddomain.SpielId;
import com.iks.dddschach.olddomain.Spielbrett;
import com.iks.dddschach.olddomain.Spielfigur;
import com.iks.dddschach.persistence.SchachpartieRepositoryMemory;
import com.iks.dddschach.service.api.SchachpartieApi;
import com.iks.dddschach.service.impl.SchachpartieApiImpl;
import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;

import static com.iks.dddschach.olddomain.Position.Zeile.*;
import static com.iks.dddschach.olddomain.Position.Spalte.*;
import static com.iks.dddschach.olddomain.SpielNotationParser.parse;


/**
 * Created by vollmer on 09.05.17.
 */
public class SchachspielApiTest {

    private final SchachpartieApi api = new SchachpartieApiImpl(new SchachpartieRepositoryMemory());
    private final static Optional<String> VERMERK = Optional.of("Vermerk");

    @Test
    public void funktionertNeueSpieleErzeugen() throws Exception {
        final NeuesSpielResponse response1 = api.neuesSpiel(new NeuesSpielRequest("Vermerk"));
        Assert.assertNotNull(response1.getSpielId());
        Assert.assertNotNull(response1.getSpielId().getId());

        final NeuesSpielResponse response2 = api.neuesSpiel(new NeuesSpielRequest("Vermerk"));
        Assert.assertNotNull(response2.getSpielId());
        Assert.assertNotNull(response2.getSpielId().getId());

        // sind die IDs von zwei neuen Spielen auch verschieden?
        Assert.assertNotEquals(response1.getSpielId(), response2.getSpielId());
        Assert.assertNotEquals(response1.getSpielId().getId(), response2.getSpielId().getId());
    }


    @Test
    public void istAmStartInitialesSchachBrettVorhanden() throws Exception {
        final NeuesSpielResponse response1 = api.neuesSpiel(new NeuesSpielRequest("Vermerk"));
        final SpielId spielId = new SpielId(response1.getSpielId().getId());
        final AktuellesSpielbrettRequest request
                = new AktuellesSpielbrettRequest(new com.iks.dddschach.domain.SpielId(spielId.id));
        final AktuellesSpielbrettResponse response = api.aktuellesSpielbrett(request);
        final Spielbrett expected = SpielbrettFactory.createInitialesSpielbrett();
        SpielbrettExt spielbrett = (SpielbrettExt)response.getSpielbrett();
        Assert.assertEquals(expected.encode(), spielbrett.encode());
    }


    @Test
    public void fuehreZuegeInVerschPartienAusUndKontrolliereStellungen() throws Exception {
        Position posFrom1 = new Position(E,_2);
        Position posTo1 = new Position(E,_4);

        Position posFrom2 = new Position(D,_2);
        Position posTo2 = new Position(D,_4);

        final Spielbrett expected1 =
                new Spielbrett(SpielbrettFactory.createInitialesSpielbrett()) {{
                    final Spielfigur figure = getSchachfigurAnPosition(posFrom1);
                    setSchachfigurAnPosition(posFrom1, null);
                    setSchachfigurAnPosition(posTo1, figure);
                }};

        final Spielbrett expected2 =
                new Spielbrett(SpielbrettFactory.createInitialesSpielbrett()) {{
                    final Spielfigur figure = getSchachfigurAnPosition(posFrom2);
                    setSchachfigurAnPosition(posFrom2, null);
                    setSchachfigurAnPosition(posTo2, figure);
                }};

        final NeuesSpielResponse response = api.neuesSpiel(new NeuesSpielRequest("Vermerk"));
        final SpielId spielId = new SpielId(response.getSpielId().getId());
        final AktuellesSpielbrettRequest request1
                = new AktuellesSpielbrettRequest(new com.iks.dddschach.domain.SpielId(spielId.id));
        api.fuehreHalbzugAus(spielId, new Halbzug(posFrom1, posTo1));

        final AktuellesSpielbrettResponse response1 = api.aktuellesSpielbrett(request1);

        final NeuesSpielResponse response2 = api.neuesSpiel(new NeuesSpielRequest("Vermerk"));
        final SpielId spielId2 = new SpielId(response2.getSpielId().getId());
        final AktuellesSpielbrettRequest request2
                = new AktuellesSpielbrettRequest(new com.iks.dddschach.domain.SpielId(spielId2.id));
        api.fuehreHalbzugAus(spielId2, new Halbzug(posFrom2, posTo2));

        final AktuellesSpielbrettResponse response3 = api.aktuellesSpielbrett(request2);

        SpielbrettExt spielbrett1 = (SpielbrettExt)response1.getSpielbrett();
        SpielbrettExt spielbrett2 = (SpielbrettExt)response3.getSpielbrett();

        Assert.assertEquals(expected1.encode(), spielbrett1.encode());
        Assert.assertEquals(expected2.encode(), spielbrett2.encode());
    }


    @Test
    public void fuehreZuegeVonWeissDanachSchwarzInEinerPartieAus() throws Exception {
        final NeuesSpielResponse response1 = api.neuesSpiel(new NeuesSpielRequest("Vermerk"));
        final SpielId spielId = new SpielId(response1.getSpielId().getId());
        api.fuehreHalbzugAus(spielId, parse("e2-e4"));
        api.fuehreHalbzugAus(spielId, parse("d7-d5"));
    }


    @Test(expected = Exception.class)
    public void ungueltigerZugWeilZweimalGleicherSpieler() throws Exception {
        final NeuesSpielResponse response1 = api.neuesSpiel(new NeuesSpielRequest("Vermerk"));
        final SpielId spielId = new SpielId(response1.getSpielId().getId());
        api.fuehreHalbzugAus(spielId, parse("e2-e4"));
        api.fuehreHalbzugAus(spielId, parse("e4-e5"));
    }


    @Test(expected = Exception.class)
    public void ungueltigerZugDennKeineSpielfigurAufStartfeld() throws Exception {
        final NeuesSpielResponse response1 = api.neuesSpiel(new NeuesSpielRequest("Vermerk"));
        final SpielId spielId = new SpielId(response1.getSpielId().getId());
        api.fuehreHalbzugAus(spielId, parse("e3-e4"));
    }

}