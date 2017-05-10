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
        final SpielIdValueObject spielId = api.neuesSpiel();
        Assert.assertNotNull(spielId);
        Assert.assertNotNull(spielId.id);
    }


    @Test
    public void schachBrett() throws Exception {
        final SpielIdValueObject spielId = api.neuesSpiel();
        final SchachbrettValueObject actual = api.schachBrett(spielId);
        final SchachbrettValueObject expected = MockDataFactory.createInitialesSchachbrett();
        Assert.assertEquals(expected, actual);
    }


    @Test
    public void fuehreZugAus() throws Exception {
        PositionValueObject posFrom1 = new PositionValueObject("e2");
        PositionValueObject posTo1 = new PositionValueObject("e4");

        final SchachbrettValueObject expected =
                new SchachbrettValueObject(MockDataFactory.createInitialesSchachbrett()) {{
                    final SpielfigurValueObject figure1 = getSchachfigurAnPosition(posFrom1);
                    setSchachfigurAnPosition(posFrom1, null);
                    setSchachfigurAnPosition(posTo1, figure1);
                }};

        final SpielIdValueObject spielId = api.neuesSpiel();
        api.fuehreHalbzugAus(spielId, new HalbzugValueObject(posFrom1, posTo1));
        final SchachbrettValueObject actual = api.schachBrett(spielId);

        Assert.assertEquals(expected, actual);
    }


    @Test
    public void fuehreZweiGueltigeZuegeAus() throws Exception {
        final SpielIdValueObject spielId = api.neuesSpiel();
        api.fuehreHalbzugAus(spielId, new HalbzugValueObject("e2-e4"));
        api.fuehreHalbzugAus(spielId, new HalbzugValueObject("d7-d5"));
    }


    @Test(expected = Exception.class)
    public void ungueltigeZuegeZweimalGleicherSpieler() throws Exception {
        final SpielIdValueObject spielId = api.neuesSpiel();
        api.fuehreHalbzugAus(spielId, new HalbzugValueObject("e2-e4"));
        api.fuehreHalbzugAus(spielId, new HalbzugValueObject("e4-e5"));
    }


    @Test(expected = Exception.class)
    public void ungueltigerZugKeineSpielfigur() throws Exception {
        final SpielIdValueObject spielId = api.neuesSpiel();
        api.fuehreHalbzugAus(spielId, new HalbzugValueObject("e3-e4"));
    }

}