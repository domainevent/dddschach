package com.iks.dddschach.domain;


/**
 * Fabrik zum Kreieren von Schachpartie-Aggregaten
 */
public class SchachpartieFactory {

    /**
     * Erzeugt eine Schachpartie mit einer (weltweit) eindeutigen ID
     * @return {@link Schachpartie$}
     */
    public Schachpartie$ createSchachpartie() {
        return new Schachpartie$(new SpielId$());
    }

}
