package com.iks.dddschach.api;

import com.iks.dddschach.domain.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 * Created by vollmer on 09.05.17.
 */
public class SchachspielApiTest {
    SchachspielApi api = new SchachspielApiImpl();

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void neuesSpiel() throws Exception {
        final SpielId spielId = api.neuesSpiel();
        Assert.assertNotNull(spielId);
        Assert.assertNotNull(spielId.id);
    }


    @Test
    public void schachBrett() throws Exception {
        final SpielId spielId = api.neuesSpiel();
        final Schachbrett actual = api.schachBrett(spielId);
        final Schachbrett expected = MockDataFactory.createInitialesSchachbrett();
        Assert.assertEquals(expected, actual);
    }


    @Test
    public void fuehreZugAus() throws Exception {
        Position posFrom1 = new Position("e2");
        Position posTo1 = new Position("e4");

        final Schachbrett expected =
                new Schachbrett(MockDataFactory.createInitialesSchachbrett()) {{
                    final Spielfigur figure1 = getSchachfigurAnPosition(posFrom1);
                    setSchachfigurAnPosition(posFrom1, null);
                    setSchachfigurAnPosition(posTo1, figure1);
                }};

        final SpielId spielId = api.neuesSpiel();
        api.fuehreHalbzugAus(spielId, new Halbzug(posFrom1, posTo1));
        final Schachbrett actual = api.schachBrett(spielId);

        Assert.assertEquals(expected, actual);
    }


    @Test
    public void fuehreZweiGueltigeZuegeAus() throws Exception {
        final SpielId spielId = api.neuesSpiel();
        api.fuehreHalbzugAus(spielId, new Halbzug("e2-e4"));
        api.fuehreHalbzugAus(spielId, new Halbzug("d7-d5"));
    }


    @Test(expected = Exception.class)
    public void ungueltigeZuegeZweimalGleicherSpieler() throws Exception {
        final SpielId spielId = api.neuesSpiel();
        api.fuehreHalbzugAus(spielId, new Halbzug("e2-e4"));
        api.fuehreHalbzugAus(spielId, new Halbzug("e4-e5"));
    }


    @Test(expected = Exception.class)
    public void ungueltigerZugKeineSpielfigur() throws Exception {
        final SpielId spielId = api.neuesSpiel();
        api.fuehreHalbzugAus(spielId, new Halbzug("e3-e4"));
    }

}