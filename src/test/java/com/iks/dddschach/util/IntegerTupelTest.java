package com.iks.dddschach.util;

import org.junit.Assert;
import org.junit.Test;


/**
 * Created by vollmer on 02.06.17.
 */
public class IntegerTupelTest {

    @Test
    public void middel1() throws Exception {
        final IntegerTupel t1 = new IntegerTupel(1, 1);
        final IntegerTupel t2 = new IntegerTupel(3, 3);
        final IntegerTupel expeceted = new IntegerTupel(2, 2);
        final IntegerTupel actual = IntegerTupel.middel(t1, t2);
        Assert.assertEquals(expeceted, actual);
    }

    @Test
    public void middel2() throws Exception {
        final IntegerTupel t1 = new IntegerTupel(1, 1);
        final IntegerTupel t2 = new IntegerTupel(4, 4);
        final IntegerTupel expeceted = new IntegerTupel(2, 2);
        final IntegerTupel actual = IntegerTupel.middel(t1, t2);
        Assert.assertEquals(expeceted, actual);
    }

    @Test
    public void middel3() throws Exception {
        final IntegerTupel t1 = new IntegerTupel(3, 3);
        final IntegerTupel t2 = new IntegerTupel(1, 1);
        final IntegerTupel expeceted = new IntegerTupel(2, 2);
        final IntegerTupel actual = IntegerTupel.middel(t1, t2);
        Assert.assertEquals(expeceted, actual);
    }

    @Test
    public void middel4() throws Exception {
        final IntegerTupel t1 = new IntegerTupel(4, 4);
        final IntegerTupel t2 = new IntegerTupel(1, 1);
        final IntegerTupel expeceted = new IntegerTupel(2, 2);
        final IntegerTupel actual = IntegerTupel.middel(t1, t2);
        Assert.assertEquals(expeceted, actual);
    }

    @Test
    public void middel5() throws Exception {
        final IntegerTupel t1 = new IntegerTupel(1, 3);
        final IntegerTupel t2 = new IntegerTupel(3, 1);
        final IntegerTupel expeceted = new IntegerTupel(2, 2);
        final IntegerTupel actual = IntegerTupel.middel(t1, t2);
        Assert.assertEquals(expeceted, actual);
    }

    @Test
    public void middel6() throws Exception {
        final IntegerTupel t1 = new IntegerTupel(1, 4);
        final IntegerTupel t2 = new IntegerTupel(4, 1);
        final IntegerTupel expeceted = new IntegerTupel(2, 3);
        final IntegerTupel actual = IntegerTupel.middel(t1, t2);
        Assert.assertEquals(expeceted, actual);
    }

    @Test
    public void middel7() throws Exception {
        final IntegerTupel t1 = new IntegerTupel(3, 1);
        final IntegerTupel t2 = new IntegerTupel(1, 3);
        final IntegerTupel expeceted = new IntegerTupel(2, 2);
        final IntegerTupel actual = IntegerTupel.middel(t1, t2);
        Assert.assertEquals(expeceted, actual);
    }

    @Test
    public void middel8() throws Exception {
        final IntegerTupel t1 = new IntegerTupel(4, 1);
        final IntegerTupel t2 = new IntegerTupel(1, 4);
        final IntegerTupel expeceted = new IntegerTupel(2, 3);
        final IntegerTupel actual = IntegerTupel.middel(t1, t2);
        Assert.assertEquals(expeceted, actual);
    }

}