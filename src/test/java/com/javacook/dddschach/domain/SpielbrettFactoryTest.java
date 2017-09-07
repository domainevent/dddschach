package com.javacook.dddschach.domain;

import org.junit.Assert;
import org.junit.Test;


/**
 * Created by vollmer on 08.07.17.
 */
public class SpielbrettFactoryTest {

    @Test
    public void decode() throws Exception {
        final String spielbrett = "RNBQKBNRPPPPPPPP________________________________pppppppprnbqkbnr";
        final Spielbrett$ decoded = SpielbrettFactory.decode(spielbrett);
        Assert.assertEquals(spielbrett, decoded.encode());
    }


}