package com.iks.dddschach.api;

import com.iks.dddschach.domain.AktuellesSpielbrettRequest;
import com.iks.dddschach.domain.AktuellesSpielbrettResponse;
import com.iks.dddschach.domain.SpielbrettExt;
import com.iks.dddschach.olddomain.*;
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
        final SpielId spielId1 = api.neuesSpiel(VERMERK);
        Assert.assertNotNull(spielId1);
        Assert.assertNotNull(spielId1.id);

        final SpielId spielId2 = api.neuesSpiel(VERMERK);
        Assert.assertNotNull(spielId2);
        Assert.assertNotNull(spielId2.id);

        // sind die IDs von zwei neuen Spielen auch verschieden?
        Assert.assertNotEquals(spielId1, spielId2);
        Assert.assertNotEquals(spielId1.id, spielId2.id);
    }


    @Test
    public void istAmStartInitialesSchachBrettVorhanden() throws Exception {
        final SpielId spielId = api.neuesSpiel(VERMERK);
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

        final SpielId spielId1 = api.neuesSpiel(VERMERK);
        final AktuellesSpielbrettRequest request1
                = new AktuellesSpielbrettRequest(new com.iks.dddschach.domain.SpielId(spielId1.id));
        api.fuehreHalbzugAus(spielId1, new Halbzug(posFrom1, posTo1));
        final AktuellesSpielbrettResponse response1 = api.aktuellesSpielbrett(request1);

        final SpielId spielId2 = api.neuesSpiel(VERMERK);
        final AktuellesSpielbrettRequest request2
                = new AktuellesSpielbrettRequest(new com.iks.dddschach.domain.SpielId(spielId2.id));
        api.fuehreHalbzugAus(spielId2, new Halbzug(posFrom2, posTo2));
        final AktuellesSpielbrettResponse response2 = api.aktuellesSpielbrett(request2);

        SpielbrettExt spielbrett1 = (SpielbrettExt)response1.getSpielbrett();
        SpielbrettExt spielbrett2 = (SpielbrettExt)response2.getSpielbrett();

        Assert.assertEquals(expected1.encode(), spielbrett1.encode());
        Assert.assertEquals(expected2.encode(), spielbrett2.encode());
    }


    @Test
    public void fuehreZuegeVonWeissDanachSchwarzInEinerPartieAus() throws Exception {
        final SpielId spielId = api.neuesSpiel(VERMERK);
        api.fuehreHalbzugAus(spielId, parse("e2-e4"));
        api.fuehreHalbzugAus(spielId, parse("d7-d5"));
    }


    @Test(expected = Exception.class)
    public void ungueltigerZugWeilZweimalGleicherSpieler() throws Exception {
        final SpielId spielId = api.neuesSpiel(VERMERK);
        api.fuehreHalbzugAus(spielId, parse("e2-e4"));
        api.fuehreHalbzugAus(spielId, parse("e4-e5"));
    }


    @Test(expected = Exception.class)
    public void ungueltigerZugDennKeineSpielfigurAufStartfeld() throws Exception {
        final SpielId spielId = api.neuesSpiel(VERMERK);
        api.fuehreHalbzugAus(spielId, parse("e3-e4"));
    }

}