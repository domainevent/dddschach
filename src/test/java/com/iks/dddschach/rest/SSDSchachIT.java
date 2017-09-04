package com.iks.dddschach.rest;

//import com.iks.dddschach.domain.*;

import com.iks.dddschach.domain.*;
import com.iks.dddschach.service.api.SchachpartieApi;
import com.iks.dddschach.service.binding_rest_client.SchachpartieRestClient;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 * Created by vollmer on 04.07.17.
 */
public class SSDSchachIT {

    public final static String CR = System.lineSeparator();

    private SchachpartieRestClient client;

    @Before
    public void before() {
        client = new SchachpartieRestClient();
    }

    @Test
    public void isAlive() {
        final SchachpartieRestClient client = new SchachpartieRestClient();
        final String date = client.isAlive();
        Assert.assertNotNull(date);
    }

    @Test
    public void neuesSpielGueltigeEingabe() {
        System.out.println("Test: neuesSpielGueltigeEingabe");
        final String gueltigerVermerk = makeStringOfLength(100);
        final NeuesSpielRequest request = new NeuesSpielRequest(gueltigerVermerk);
        final NeuesSpielResponse response = client.neuesSpiel(request);
        Assert.assertNotNull(response.getSpielId().getId());
    }

    @Test
    public void neuesSpielUngueltigeEingabe() {
        System.out.println("Test: neuesSpielUngueltigeEingabe");
        final SchachpartieRestClient client = new SchachpartieRestClient();
        final String ungueltigerVermerk = makeStringOfLength(101);
        final NeuesSpielRequest request = new NeuesSpielRequest(ungueltigerVermerk);
        try {
            client.neuesSpiel(request);
            Assert.fail("Expected RestCallFailedException not occured");
        }
        catch (SchachpartieRestClient.RestCallFailedException e) {
            Assert.assertEquals(400, e.statusCode);
        }
    }


    @Test
    public void spielBrettUngueltigeSpielId() {
        System.out.println("Test: spielBrettUngueltigeSpielId");
        final SchachpartieRestClient client = new SchachpartieRestClient();
        final AktuellesSpielbrettRequest request = new AktuellesSpielbrettRequest(null, new SpielId$());
        try {
            client.aktuellesSpielbrett(request);
            Assert.fail("Expected UngueltigeSpielIdException not occured");
        }
        catch (SchachpartieApi.UngueltigeSpielIdException e) {
            // expected case
        }
    }


    @Test
    public void spielBrettGueltigeSpielId() throws Exception {
        System.out.println("Test: spielBrettGueltigeSpielId");
        final SchachpartieRestClient client = new SchachpartieRestClient();

        final NeuesSpielRequest neuesSpielRequest = new NeuesSpielRequest("Vermerk");
        final SpielId$ spielId = (SpielId$) client.neuesSpiel(neuesSpielRequest).getSpielId();

        final AktuellesSpielbrettRequest aktuellesSpielbrettRequest = new AktuellesSpielbrettRequest(null, spielId);
        final AktuellesSpielbrettResponse response = client.aktuellesSpielbrett(aktuellesSpielbrettRequest);
        Assert.assertEquals(SpielbrettFactory.createInitialesSpielbrett(), response.getSpielbrett());
    }
//
//
//    @Test
//    public void spielBrettWiederholteAbfrageMitGleichemSpieler() throws Exception {
//        System.out.println("Test: spielBrettWiederholteAbfrageMitGleichemSpieler");
//        final SchachpartieRestClient client = new SchachpartieRestClient();
//        final SpielId spielId = client.neuesSpiel("Vermerk");
//        final Response resp1 =  client.spielbrett(spielId.getId(), "Test", null);
//        final EntityTag etag = resp1.getEntityTag();
//        final Response resp2 = client.spielbrettEtag(spielId.getId(), "Test", etag.getValue());
//        Assert.assertEquals(304, resp2.getStatus());
//    }
//
//
//    @Test
//    public void spielBrettWiederholteAbfrageMitAnderemSpieler() throws Exception {
//        System.out.println("Test: spielBrettWiederholteAbfrageMitAnderemSpieler");
//        final SchachpartieRestClient client = new SchachpartieRestClient();
//        final SpielId spielId = client.neuesSpiel("Vermerk");
//        final Response resp1 =  client.spielbrett(spielId.getId(), "Test1", null);
//        final EntityTag etag = resp1.getEntityTag();
//        final Response resp2 = client.spielbrettEtag(spielId.getId(), "Test2", etag.getValue());
//        Assert.assertEquals(200, resp2.getStatus());
//    }
//
//
//    @Test
//    public void spielBrettWiederholteAbfrageMitZwischenzeitlichenHalbzug() throws Exception {
//        System.out.println("Test: spielBrettWiederholteAbfrageMitGleichemSpieler");
//        final SchachpartieRestClient client = new SchachpartieRestClient();
//        final SpielId spielId = client.neuesSpiel("Vermerk");
//        final Response resp1 =  client.spielbrett(spielId.getId(), "Test", null);
//        final EntityTag etag = resp1.getEntityTag();
//        client.fuehreHalbzugAus(spielId.getId(), "e2-e4");
//        final Response resp2 = client.spielbrettEtag(spielId.getId(), "Test", etag.getValue());
//        Assert.assertEquals(200, resp2.getStatus());
//    }
//
//
//    @Test
//    public void fuehreGueltigenHalbzugAus() throws Exception {
//        System.out.println("Test: fuehreGueltigenHalbzugAus");
//        final SchachpartieRestClient client = new SchachpartieRestClient();
//
//        final SpielId spielId = client.neuesSpiel("Vermerk");
//        // Halbzug ausführen:
//        //
//        final Halbzug$ halbzug = new Halbzug$(new Position$(E, II), new Position$(E, IV));
//        final Response resp1 = client.fuehreHalbzugAus(spielId.getId(), halbzug);
//        Assert.assertEquals(201, resp1.getStatus());
//        // Spielbrett überprüfen:
//        //
//        final Response resp2 = client.spielbrett(spielId.getId(), "Tester", null);
//        final Spielbrett$ actual = resp2.readEntity(Spielbrett$.class);
//        final Spielbrett$ expected = SpielbrettFactory.createInitialesSpielbrett().wendeHalbzugAn(halbzug);
//        Assert.assertEquals(expected, actual);
//    }
//
//
//    String[] UNSTERBLICHE_PARTIE = {
//            "e2-e4", "e7-e5", "f2-f4", "e5-f4", "f1-c4", "d8-h4", "e1-f1", "b7-b5", "c4-b5",
//            "g8-f6", "g1-f3", "h4-h6", "d2-d3", "f6-h5", "f3-h4", "h6-g5", "h4-f5", "c7-c6",
//            "g2-g4", "h5-f6", "h1-g1", "c6-b5", "h2-h4", "g5-g6", "h4-h5", "g6-g5", "d1-f3",
//            "f6-g8", "c1-f4", "g5-f6", "b1-c3", "f8-c5", "c3-d5", "f6-b2", "f4-d6", "c5-g1",
//            "e4-e5", "b2-a1", "f1-e2", "b8-a6", "f5-g7", "e8-d8", "f3-f6", "g8-f6", "d6-e7"
//    };
//
//    String FINALES_SPIELBRETT =
//                    "-------------------------" +CR+
//                    "|Rb|  |Bb|Kb|  |  |  |Rb|" +CR+
//                    "-------------------------" +CR+
//                    "|Pb|  |  |Pb|Bw|Pb|Nw|Pb|" +CR+
//                    "-------------------------" +CR+
//                    "|Nb|  |  |  |  |Nb|  |  |" +CR+
//                    "-------------------------" +CR+
//                    "|  |Pb|  |Nw|Pw|  |  |Pw|" +CR+
//                    "-------------------------" +CR+
//                    "|  |  |  |  |  |  |Pw|  |" +CR+
//                    "-------------------------" +CR+
//                    "|  |  |  |Pw|  |  |  |  |" +CR+
//                    "-------------------------" +CR+
//                    "|Pw|  |Pw|  |Kw|  |  |  |" +CR+
//                    "-------------------------" +CR+
//                    "|Qb|  |  |  |  |  |Bb|  |" +CR+
//                    "-------------------------";
//
//    @Test
//    public void unsterblichePartie() throws Exception {
//        System.out.println("Test: unsterblichePartie");
//        final SchachpartieRestClient client = new SchachpartieRestClient();
//
//        // Spiel erstellen:
//        //
//        final SpielId spielId = client.neuesSpiel("Vermerk");
//
//        // alle Züge sukzessiv ausführen:
//        //
//        for (String halbzugStr : UNSTERBLICHE_PARTIE) {
//            final Halbzug$ halbzug = SpielNotationParser.parse(halbzugStr);
//            client.fuehreHalbzugAus(spielId.getId(), halbzug.encode());
//        }
//        // Versuch, Zug auszuführen, nachdem Schwarz schon matt ist:
//        //
//        final Halbzug$ halbzug = SpielNotationParser.parse("d8-c7");
//        try {
//            client.fuehreHalbzugAus(spielId.getId(), halbzug.encode());
//            Assert.fail("Expected UngueltigerHalbzugException not occured");
//        }
//        catch (UngueltigerHalbzugException e) {
//            Assert.assertEquals(Zugregel.DIE_PARTIE_ENDET_MATT, e.verletzteZugregel);
//        }
//        // Spielbrett überprüfen:
//        //
//        final Response resp = client.spielbrett(spielId.getId(), "Tester", null);
//        final Spielbrett$ actual = resp.readEntity(Spielbrett$.class);
//        Assert.assertEquals(FINALES_SPIELBRETT, actual.toString());
//    }


    private static String makeStringOfLength(int n) {
        String result = "";
        for (int i = 0; i < n; i++) result += (char)i;
        return result;
    }

}
