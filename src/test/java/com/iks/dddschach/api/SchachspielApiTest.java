package com.iks.dddschach.api;

import com.iks.dddschach.domain.*;
import com.iks.dddschach.olddomain.Halbzug;
import com.iks.dddschach.olddomain.Position;
import com.iks.dddschach.olddomain.Spielbrett;
import com.iks.dddschach.olddomain.*;
import com.iks.dddschach.olddomain.Spielfigur;
import com.iks.dddschach.persistence.SchachpartieRepositoryMemory;
import com.iks.dddschach.service.api.SchachpartieApi;
import com.iks.dddschach.service.impl.SchachpartieApiImpl;
import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;

import static com.iks.dddschach.olddomain.Position.Spalte.D;
import static com.iks.dddschach.olddomain.Position.Spalte.E;
import static com.iks.dddschach.olddomain.Position.Zeile._2;
import static com.iks.dddschach.olddomain.Position.Zeile._4;


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

        final AktuellesSpielbrettRequest request = new AktuellesSpielbrettRequest(response1.getSpielId());
        final AktuellesSpielbrettResponse response2 = api.aktuellesSpielbrett(request);

        final Spielbrett expected = SpielbrettFactory.createInitialesSpielbrett();
        SpielbrettExt spielbrett = (SpielbrettExt)response2.getSpielbrett();
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

        final NeuesSpielResponse neuesSpielResponse1 = api.neuesSpiel(new NeuesSpielRequest("Vermerk"));
        final Halbzug halbzugOld1 = new Halbzug(posFrom1, posTo1);
        final FuehreHalbzugAusRequest fuehreHalbzugAusRequest1 = new FuehreHalbzugAusRequest(neuesSpielResponse1.getSpielId(), HalbzugExt.fromOld(halbzugOld1));

        api.fuehreHalbzugAus(fuehreHalbzugAusRequest1);

        final AktuellesSpielbrettRequest aktuellesSpielbrettRequest1 = new AktuellesSpielbrettRequest(neuesSpielResponse1.getSpielId());
        final AktuellesSpielbrettResponse aktuellesSpielbrettResponse1 = api.aktuellesSpielbrett(aktuellesSpielbrettRequest1);

        final NeuesSpielResponse neuesSpielResponse2 = api.neuesSpiel(new NeuesSpielRequest("Vermerk"));
        final Halbzug halbzugOld2 = new Halbzug(posFrom2, posTo2);

        final FuehreHalbzugAusRequest fuehreHalbzugAusRequest2 = new FuehreHalbzugAusRequest(neuesSpielResponse2.getSpielId(), HalbzugExt.fromOld(halbzugOld2));
        api.fuehreHalbzugAus(fuehreHalbzugAusRequest2);

        final AktuellesSpielbrettRequest aktuellesSpielbrettRequest2
                = new AktuellesSpielbrettRequest(neuesSpielResponse2.getSpielId());

        final AktuellesSpielbrettResponse aktuellesSpielbrettResponse2 = api.aktuellesSpielbrett(aktuellesSpielbrettRequest2);

        SpielbrettExt spielbrett1 = (SpielbrettExt)aktuellesSpielbrettResponse1.getSpielbrett();
        SpielbrettExt spielbrett2 = (SpielbrettExt)aktuellesSpielbrettResponse2.getSpielbrett();

        Assert.assertEquals(expected1.encode(), spielbrett1.encode());
        Assert.assertEquals(expected2.encode(), spielbrett2.encode());
    }


    @Test
    public void fuehreZuegeVonWeissDanachSchwarzInEinerPartieAus() throws Exception {
        final NeuesSpielResponse response1 = api.neuesSpiel(new NeuesSpielRequest("Vermerk"));
        final FuehreHalbzugAusRequest fuehreHalbzugAusRequest1 = new FuehreHalbzugAusRequest(response1.getSpielId(), new HalbzugExt("e2-e4"));
        api.fuehreHalbzugAus(fuehreHalbzugAusRequest1);
        final FuehreHalbzugAusRequest fuehreHalbzugAusRequest2 = new FuehreHalbzugAusRequest(response1.getSpielId(), new HalbzugExt("d7-d5"));
        api.fuehreHalbzugAus(fuehreHalbzugAusRequest2);
    }


    @Test(expected = Exception.class)
    public void ungueltigerZugWeilZweimalGleicherSpieler() throws Exception {
        final NeuesSpielResponse response1 = api.neuesSpiel(new NeuesSpielRequest("Vermerk"));
        final FuehreHalbzugAusRequest fuehreHalbzugAusRequest1 = new FuehreHalbzugAusRequest(response1.getSpielId(), new HalbzugExt("e2-e4"));
        api.fuehreHalbzugAus(fuehreHalbzugAusRequest1);
        final FuehreHalbzugAusRequest fuehreHalbzugAusRequest2 = new FuehreHalbzugAusRequest(response1.getSpielId(), new HalbzugExt("e4-e5"));
        api.fuehreHalbzugAus(fuehreHalbzugAusRequest2);
    }


    @Test(expected = Exception.class)
    public void ungueltigerZugDennKeineSpielfigurAufStartfeld() throws Exception {
        final NeuesSpielResponse response1 = api.neuesSpiel(new NeuesSpielRequest("Vermerk"));
        final FuehreHalbzugAusRequest fuehreHalbzugAusRequest1 = new FuehreHalbzugAusRequest(response1.getSpielId(), new HalbzugExt("e3-e4"));
        api.fuehreHalbzugAus(fuehreHalbzugAusRequest1);
    }

}