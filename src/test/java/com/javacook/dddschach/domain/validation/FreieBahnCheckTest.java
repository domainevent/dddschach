package com.javacook.dddschach.domain.validation;

import com.javacook.dddschach.domain.*;
import org.junit.Assert;
import org.junit.Test;


/**
 * Created by vollmer on 01.06.17.
 */
public class FreieBahnCheckTest {

    public final static Spielbrett SPIELBRETT = SpielbrettFactory.createInitialesSpielbrett();
    public final static FreieBahnCheck FREIE_BAHN_CHECK = new FreieBahnCheck();


    @Test
    public void checkGueltigenZug1() throws Exception {
        final Halbzug halbzug = new Halbzug(new Position(Spalte.B, Zeile.II), new Position(Spalte.B, Zeile.III));
        Assert.assertTrue(FREIE_BAHN_CHECK.success(halbzug, SPIELBRETT));
    }


    @Test
    public void checkGueltigenZug2() throws Exception {
        final Halbzug halbzug = new Halbzug(new Position(Spalte.B, Zeile.II), new Position(Spalte.B, Zeile.IV));
        Assert.assertTrue(FREIE_BAHN_CHECK.success(halbzug, SPIELBRETT));
    }


    @Test
    public void checkGueltigenZug3() throws Exception {
        final Halbzug halbzug = new Halbzug(new Position(Spalte.B, Zeile.II), new Position(Spalte.B, Zeile.VII));
        Assert.assertTrue(FREIE_BAHN_CHECK.success(halbzug, SPIELBRETT));
    }


    @Test
    public void checkGueltigenZug4() throws Exception {
        final Halbzug halbzug = new Halbzug(new Position(Spalte.E, Zeile.II), new Position(Spalte.F, Zeile.I));
        Assert.assertTrue(FREIE_BAHN_CHECK.success(halbzug, SPIELBRETT));
    }


    @Test
    public void checkGueltigenZug5() throws Exception {
        final Halbzug halbzug = new Halbzug(new Position(Spalte.G, Zeile.II), new Position(Spalte.B, Zeile.VII));
        Assert.assertTrue(FREIE_BAHN_CHECK.success(halbzug, SPIELBRETT));
    }


    @Test
    public void checkUngueltigenZug1() throws Exception {
        final Halbzug halbzug = new Halbzug(new Position(Spalte.C, Zeile.I), new Position(Spalte.E, Zeile.III));
        Assert.assertFalse(FREIE_BAHN_CHECK.success(halbzug, SPIELBRETT));
    }


    @Test
    public void checkUngueltigenZug2() throws Exception {
        final Halbzug halbzug = new Halbzug(new Position(Spalte.H, Zeile.VIII), new Position(Spalte.H, Zeile.VI));
        Assert.assertFalse(FREIE_BAHN_CHECK.success(halbzug, SPIELBRETT));
    }


    @Test
    public void checkUngueltigenZug3() throws Exception {
        final Halbzug halbzug = new Halbzug(new Position(Spalte.G, Zeile.VIII), new Position(Spalte.F, Zeile.VI));
        Assert.assertFalse(FREIE_BAHN_CHECK.success(halbzug, SPIELBRETT));
    }


    @Test
    public void checkUngueltigenZug4() throws Exception {
        final Halbzug halbzug = new Halbzug(new Position(Spalte.A, Zeile.I), new Position(Spalte.H, Zeile.VIII));
        Assert.assertFalse(FREIE_BAHN_CHECK.success(halbzug, SPIELBRETT));
    }


}