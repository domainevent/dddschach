package com.iks.dddschach.api;

import com.iks.dddschach.domain.*;
import com.iks.dddschach.util.SampleDataFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;


/**
 * Created by vollmer on 09.05.17.
 */
public class SchachspielApiTest {

    private SchachpartieApi api = new SchachpartieApiImpl();
    private final static Optional VERMERK = Optional.of("Vermerk");

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void neuesSpiel() throws Exception {
        final SpielId spielId = api.neuesSpiel(VERMERK);
        Assert.assertNotNull(spielId);
        Assert.assertNotNull(spielId.id);
    }


    @Test
    public void schachBrett() throws Exception {
        final SpielId spielId = api.neuesSpiel(VERMERK);
        final Spielbrett actual = api.schachBrett(spielId);
        final Spielbrett expected = SampleDataFactory.createInitialesSchachbrett();
        Assert.assertEquals(expected, actual);
    }


    @Test
    public void fuehreZugAus() throws Exception {
        Position posFrom1 = new Position("e2");
        Position posTo1 = new Position("e4");

        final Spielbrett expected =
                new Spielbrett(SampleDataFactory.createInitialesSchachbrett()) {{
                    final Spielfigur figure1 = getSchachfigurAnPosition(posFrom1);
                    setSchachfigurAnPosition(posFrom1, null);
                    setSchachfigurAnPosition(posTo1, figure1);
                }};

        final SpielId spielId = api.neuesSpiel(VERMERK);
        api.fuehreHalbzugAus(spielId, new Halbzug(posFrom1, posTo1));
        final Spielbrett actual = api.schachBrett(spielId);

        Assert.assertEquals(expected, actual);
    }


    @Test
    public void fuehreZweiGueltigeZuegeAus() throws Exception {
        final SpielId spielId = api.neuesSpiel(VERMERK);
        api.fuehreHalbzugAus(spielId, new Halbzug("e2-e4"));
        api.fuehreHalbzugAus(spielId, new Halbzug("d7-d5"));
    }


    @Test(expected = Exception.class)
    public void ungueltigeZuegeZweimalGleicherSpieler() throws Exception {
        final SpielId spielId = api.neuesSpiel(VERMERK);
        api.fuehreHalbzugAus(spielId, new Halbzug("e2-e4"));
        api.fuehreHalbzugAus(spielId, new Halbzug("e4-e5"));
    }


    @Test(expected = Exception.class)
    public void ungueltigerZugKeineSpielfigur() throws Exception {
        final SpielId spielId = api.neuesSpiel(VERMERK);
        api.fuehreHalbzugAus(spielId, new Halbzug("e3-e4"));
    }

}