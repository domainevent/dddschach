package com.iks.dddschach.domain.validation;

import com.iks.dddschach.domain.Halbzug;
import com.iks.dddschach.domain.Position;
import com.iks.dddschach.domain.Spielbrett;
import com.iks.dddschach.domain.SpielbrettFactory;
import org.junit.Assert;
import org.junit.Test;

import static com.iks.dddschach.domain.Position.Spalte.*;
import static com.iks.dddschach.domain.Position.Zeile.*;


/**
 * Created by vollmer on 01.06.17.
 */
public class FreieBahnCheckTest {

    public final static Spielbrett SPIELBRETT = SpielbrettFactory.createInitialesSpielbrett();
    public final static FreieBahnCheck FREIE_BAHN_CHECK = new FreieBahnCheck();


    @Test
    public void checkGueltigenZug1() throws Exception {
        final Halbzug halbzug = new Halbzug(new Position(B, _2), new Position(B, _3));
        Assert.assertTrue(FREIE_BAHN_CHECK.success(halbzug, SPIELBRETT));
    }


    @Test
    public void checkGueltigenZug2() throws Exception {
        final Halbzug halbzug = new Halbzug(new Position(B, _2), new Position(B, _4));
        Assert.assertTrue(FREIE_BAHN_CHECK.success(halbzug, SPIELBRETT));
    }


    @Test
    public void checkGueltigenZug3() throws Exception {
        final Halbzug halbzug = new Halbzug(new Position(B, _2), new Position(B, _7));
        Assert.assertTrue(FREIE_BAHN_CHECK.success(halbzug, SPIELBRETT));
    }


    @Test
    public void checkGueltigenZug4() throws Exception {
        final Halbzug halbzug = new Halbzug(new Position(E, _2), new Position(F, _1));
        Assert.assertTrue(FREIE_BAHN_CHECK.success(halbzug, SPIELBRETT));
    }


    @Test
    public void checkGueltigenZug5() throws Exception {
        final Halbzug halbzug = new Halbzug(new Position(G, _2), new Position(B, _7));
        Assert.assertTrue(FREIE_BAHN_CHECK.success(halbzug, SPIELBRETT));
    }


    @Test
    public void checkUngueltigenZug1() throws Exception {
        final Halbzug halbzug = new Halbzug(new Position(C, _1), new Position(E, _3));
        Assert.assertFalse(FREIE_BAHN_CHECK.success(halbzug, SPIELBRETT));
    }


    @Test
    public void checkUngueltigenZug2() throws Exception {
        final Halbzug halbzug = new Halbzug(new Position(H, _8), new Position(H, _6));
        Assert.assertFalse(FREIE_BAHN_CHECK.success(halbzug, SPIELBRETT));
    }


    @Test
    public void checkUngueltigenZug3() throws Exception {
        final Halbzug halbzug = new Halbzug(new Position(G, _8), new Position(F, _6));
        Assert.assertFalse(FREIE_BAHN_CHECK.success(halbzug, SPIELBRETT));
    }


    @Test
    public void checkUngueltigenZug4() throws Exception {
        final Halbzug halbzug = new Halbzug(new Position(A, _1), new Position(H, _8));
        Assert.assertFalse(FREIE_BAHN_CHECK.success(halbzug, SPIELBRETT));
    }


}