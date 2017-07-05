package com.iks.dddschach.rest;

import com.iks.dddschach.api.SchachpartieApi;
import com.iks.dddschach.domain.SpielId;
import com.iks.dddschach.domain.Spielbrett;
import com.iks.dddschach.domain.SpielbrettFactory;
import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.core.Response;


/**
 * Created by vollmer on 04.07.17.
 */
public class DDDSchachIT {


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
            Assert.assertEquals(SpielbrettFactory.createInitialesSpielbrett(), spielbrett);
        }
        catch (SchachpartieApi.UngueltigeSpielIdException e) {
            Assert.fail(e.toString());
        }
    }


    private static String makeStringOfLength(int n) {
        String result = "";
        for (int i = 0; i < n; i++) result += (char)i;
        return result;
    }

}
