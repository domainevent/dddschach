package com.iks.dddschach.domain.validation;

import com.iks.dddschach.domain.*;
import org.junit.Assert;
import org.junit.Test;

import static com.iks.dddschach.domain.Spalte.*;
import static com.iks.dddschach.domain.Zeile.*;


/**
 * Created by vollmer on 01.06.17.
 */
public class FreieBahnCheckTest {

    public final static Spielbrett$ SPIELBRETT = SpielbrettFactory.createInitialesSpielbrett();
    public final static FreieBahnCheck FREIE_BAHN_CHECK = new FreieBahnCheck();


    @Test
    public void checkGueltigenZug1() throws Exception {
        final Halbzug$ halbzug = new Halbzug$(new Position$(B, II), new Position$(B, III));
        Assert.assertTrue(FREIE_BAHN_CHECK.success(halbzug, SPIELBRETT));
    }


    @Test
    public void checkGueltigenZug2() throws Exception {
        final Halbzug$ halbzug = new Halbzug$(new Position$(B, II), new Position$(B, IV));
        Assert.assertTrue(FREIE_BAHN_CHECK.success(halbzug, SPIELBRETT));
    }


    @Test
    public void checkGueltigenZug3() throws Exception {
        final Halbzug$ halbzug = new Halbzug$(new Position$(B, II), new Position$(B, VII));
        Assert.assertTrue(FREIE_BAHN_CHECK.success(halbzug, SPIELBRETT));
    }


    @Test
    public void checkGueltigenZug4() throws Exception {
        final Halbzug$ halbzug = new Halbzug$(new Position$(E, II), new Position$(F, I));
        Assert.assertTrue(FREIE_BAHN_CHECK.success(halbzug, SPIELBRETT));
    }


    @Test
    public void checkGueltigenZug5() throws Exception {
        final Halbzug$ halbzug = new Halbzug$(new Position$(G, II), new Position$(B, VII));
        Assert.assertTrue(FREIE_BAHN_CHECK.success(halbzug, SPIELBRETT));
    }


    @Test
    public void checkUngueltigenZug1() throws Exception {
        final Halbzug$ halbzug = new Halbzug$(new Position$(C, I), new Position$(E, III));
        Assert.assertFalse(FREIE_BAHN_CHECK.success(halbzug, SPIELBRETT));
    }


    @Test
    public void checkUngueltigenZug2() throws Exception {
        final Halbzug$ halbzug = new Halbzug$(new Position$(H, VIII), new Position$(H, VI));
        Assert.assertFalse(FREIE_BAHN_CHECK.success(halbzug, SPIELBRETT));
    }


    @Test
    public void checkUngueltigenZug3() throws Exception {
        final Halbzug$ halbzug = new Halbzug$(new Position$(G, VIII), new Position$(F, VI));
        Assert.assertFalse(FREIE_BAHN_CHECK.success(halbzug, SPIELBRETT));
    }


    @Test
    public void checkUngueltigenZug4() throws Exception {
        final Halbzug$ halbzug = new Halbzug$(new Position$(A, I), new Position$(H, VIII));
        Assert.assertFalse(FREIE_BAHN_CHECK.success(halbzug, SPIELBRETT));
    }


}