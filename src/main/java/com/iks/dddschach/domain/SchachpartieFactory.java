package com.iks.dddschach.domain;

/**
 * Created by vollmer on 18.05.17.
 */
public class SchachpartieFactory {

    public static Schachpartie createSchachpartie() {
        return new Schachpartie(new SpielId());
    }

}
