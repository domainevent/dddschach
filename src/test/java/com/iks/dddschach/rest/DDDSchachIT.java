package com.iks.dddschach.rest;

import com.iks.dddschach.api.SchachpartieApi;
import com.iks.dddschach.api.SchachpartieApi.UngueltigerHalbzugException;
import com.iks.dddschach.domain.*;
import com.iks.dddschach.domain.validation.Zugregel;
import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.core.Response;

import java.text.ParseException;

import static com.iks.dddschach.domain.Position.Spalte.E;
import static com.iks.dddschach.domain.Position.Zeile._2;
import static com.iks.dddschach.domain.Position.Zeile._4;
import static com.iks.dddschach.domain.SpielbrettFactory.createInitialesSpielbrett;


/**
 * Created by vollmer on 04.07.17.
 */
public class DDDSchachIT {

    public final static String CR = System.lineSeparator();

    @Test
    public void isAlive() {
        final RestServiceClient client = new RestServiceClient();
        final String date = client.isAlive();
        Assert.assertNotNull(date);
    }

    @Test
    public void neuesSpielGueltigeEingabe() {
        System.out.println("Test: neuesSpielGueltigeEingabe");
        final String gueltigerVermerk = makeStringOfLength(100);

        final RestServiceClient client = new RestServiceClient();
        final SpielId spielId = client.neuesSpiel(gueltigerVermerk);
        Assert.assertNotNull(spielId.id);
    }

    @Test
    public void neuesSpielUngueltigeEingabe() {
        System.out.println("Test: neuesSpielUngueltigeEingabe");
        final RestServiceClient client = new RestServiceClient();
        final String ungueltigerVermerk = makeStringOfLength(101);
        try {
            client.neuesSpiel(ungueltigerVermerk);
            Assert.fail("Expected RestCallFailedException not occured");
        }
        catch (RestServiceClient.RestCallFailedException e) {
            Assert.assertEquals(400, e.statusCode);
        }
    }


    @Test
    public void spielBrettUngueltigeSpielId() {
        System.out.println("Test: spielBrettUngueltigeSpielId");
        final RestServiceClient client = new RestServiceClient();
        try {
            client.spielbrett("", "", null);
            Assert.fail("Expected UngueltigeSpielIdException not occured");
        }
        catch (SchachpartieApi.UngueltigeSpielIdException e) {
            // expected case
        }
    }

    @Test
    public void spielBrettGueltigeSpielId() {
        System.out.println("Test: spielBrettGueltigeSpielId");
        final RestServiceClient client = new RestServiceClient();
        try {
            final SpielId spielId = client.neuesSpiel("Vermerk");
            final Response resp = client.spielbrett(spielId.id, "Test", null);
            Assert.assertEquals(200, resp.getStatus());
            final Spielbrett spielbrett = resp.readEntity(Spielbrett.class);
            Assert.assertEquals(createInitialesSpielbrett(), spielbrett);
        }
        catch (SchachpartieApi.UngueltigeSpielIdException e) {
            Assert.fail(e.toString());
        }
    }

    @Test
    public void fuehreGueltigenHalbzugAus() throws Exception {
        System.out.println("Test: fuehreGueltigenHalbzugAus");
        final RestServiceClient client = new RestServiceClient();

        final SpielId spielId = client.neuesSpiel("Vermerk");
        // Halbzug ausführen:
        //
        final Halbzug halbzug = new Halbzug(new Position(E, _2), new Position(E, _4));
        final Response resp1 = client.fuehreHalbzugAus(spielId.id, halbzug.toString());
        Assert.assertEquals(201, resp1.getStatus());
        // Spielbrett überprüfen:
        //
        final Response resp2 = client.spielbrett(spielId.id, "Tester", null);
        final Spielbrett actual = resp2.readEntity(Spielbrett.class);
        final Spielbrett expected = createInitialesSpielbrett().wendeHalbzugAn(halbzug);
        Assert.assertEquals(expected, actual);
    }


    String[] UNSTERBLICHE_PARTY = {
            "e2-e4", "e7-e5", "f2-f4", "e5-f4", "f1-c4", "d8-h4", "e1-f1", "b7-b5", "c4-b5",
            "g8-f6", "g1-f3", "h4-h6", "d2-d3", "f6-h5", "f3-h4", "h6-g5", "h4-f5", "c7-c6",
            "g2-g4", "h5-f6", "h1-g1", "c6-b5", "h2-h4", "g5-g6", "h4-h5", "g6-g5", "d1-f3",
            "f6-g8", "c1-f4", "g5-f6", "b1-c3", "f8-c5", "c3-d5", "f6-b2", "f4-d6", "c5-g1",
            "e4-e5", "b2-a1", "f1-e2", "b8-a6", "f5-g7", "e8-d8", "f3-f6", "g8-f6", "d6-e7"
    };

    String FINALES_SPIELBRETT =
                    "-------------------------" +CR+
                    "|Rb|  |Bb|Kb|  |  |  |Rb|" +CR+
                    "-------------------------" +CR+
                    "|Pb|  |  |Pb|Bw|Pb|Nw|Pb|" +CR+
                    "-------------------------" +CR+
                    "|Nb|  |  |  |  |Nb|  |  |" +CR+
                    "-------------------------" +CR+
                    "|  |Pb|  |Nw|Pw|  |  |Pw|" +CR+
                    "-------------------------" +CR+
                    "|  |  |  |  |  |  |Pw|  |" +CR+
                    "-------------------------" +CR+
                    "|  |  |  |Pw|  |  |  |  |" +CR+
                    "-------------------------" +CR+
                    "|Pw|  |Pw|  |Kw|  |  |  |" +CR+
                    "-------------------------" +CR+
                    "|Qb|  |  |  |  |  |Bb|  |" +CR+
                    "-------------------------";

    @Test
    public void unsterblicheParty() throws Exception {
        System.out.println("Test: unsterblicheParty");
        final RestServiceClient client = new RestServiceClient();

        // Spiel erstellen:
        //
        final SpielId spielId = client.neuesSpiel("Vermerk");

        // alle Züge sukzessiv ausführen:
        //
        for (String halbzugStr : UNSTERBLICHE_PARTY) {
            final Halbzug halbzug = SpielNotationParser.parse(halbzugStr);
            client.fuehreHalbzugAus(spielId.id, halbzug.toString());
        }
        // Versuch, Zug auszuführen, nachdem Schwarz schon matt ist:
        //
        final Halbzug halbzug = SpielNotationParser.parse("d8-c7");
        try {
            client.fuehreHalbzugAus(spielId.id, halbzug.toString());
            Assert.fail("Expected UngueltigerHalbzugException not occured");
        }
        catch (UngueltigerHalbzugException e) {
            Assert.assertEquals(Zugregel.DIE_PARTIE_ENDET_MATT, e.verletzteZugregel);
        }
        // Spielbrett überprüfen:
        //
        final Response resp2 = client.spielbrett(spielId.id, "Tester", null);
        final Spielbrett actual = resp2.readEntity(Spielbrett.class);
        Assert.assertEquals(FINALES_SPIELBRETT, actual.toString());
    }


    private static String makeStringOfLength(int n) {
        String result = "";
        for (int i = 0; i < n; i++) result += (char)i;
        return result;
    }

}
