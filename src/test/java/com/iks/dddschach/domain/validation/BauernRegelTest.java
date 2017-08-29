package com.iks.dddschach.domain.validation;

import com.iks.dddschach.domain.Halbzug;
import com.iks.dddschach.domain.Spielbrett$;
import com.iks.dddschach.domain.SpielbrettFactory;
import com.iks.dddschach.domain.SpielNotationParser;
import com.iks.dddschach.domain.validation.HalbzugValidation.ValidationResult;
import org.junit.Assert;
import org.junit.Test;

import static com.iks.dddschach.domain.validation.Zugregel.BAUER_ZIEHT_EIN_FELD_VORWAERTS_AUSSER_AM_ANFANG_ZWEI;


/**
 * Created by vollmer on 08.07.17.
 */
public class BauernRegelTest {

    public final static Spielbrett$ SPIELBRETT = SpielbrettFactory.createInitialesSpielbrett();
    public final static BauernRegel BAUERN_REGEL = new BauernRegel();

    @Test
    public void checkValideStartZuege() throws Exception {
        String[] valideStartzuege = {
                "a2-a3", "b2-b3", "c2-c3", "d2-d3", "e2-e3", "f2-f3", "g2-g3", "h2-h3",
                "a2-a4", "b2-b4", "c2-c4", "d2-d4", "e2-e4", "f2-f4", "g2-g4", "h2-h4",
                "a7-a6", "b7-b6", "c7-c6", "d7-d6", "e7-e6", "f7-f6", "g7-g6", "h7-h6",
                "a7-a5", "b7-b5", "c7-c5", "d7-d5", "e7-e5", "f7-f5", "g7-g5", "h7-h5",
        };
        for (String str : valideStartzuege) {
            final Halbzug halbzug = SpielNotationParser.parse(str);
            final ValidationResult result = BAUERN_REGEL.validiere(halbzug, null, SPIELBRETT);
            Assert.assertTrue(result.gueltig);
        }
    }

    @Test
    public void checkUngueltigeStartZuege() throws Exception {
        String[] valideStartzuege = {
                "a2-a1", "b2-b2", "c2-c5", "d2-d6", "e2-e7", "f2-f8", "g2-g2"
        };
        for (String str : valideStartzuege) {
            final Halbzug halbzug = SpielNotationParser.parse(str);
            final ValidationResult result = BAUERN_REGEL.validiere(halbzug, null, SPIELBRETT);
            Assert.assertFalse(result.gueltig);
            Assert.assertEquals(BAUER_ZIEHT_EIN_FELD_VORWAERTS_AUSSER_AM_ANFANG_ZWEI, result.verletzteZugregel);
        }
    }

    @Test(expected = NullPointerException.class)
    public void checkFigurFehlt() throws Exception {
        final Halbzug halbzug = SpielNotationParser.parse("a3-a4");
        final ValidationResult result = BAUERN_REGEL.validiere(halbzug, null, SPIELBRETT);
    }

}