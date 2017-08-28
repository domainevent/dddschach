package com.iks.dddschach.olddomain;

import com.iks.dddschach.domain.SchachpartieExt;
import com.iks.dddschach.domain.SpielId;


/**
 * Fabrik zum Kreieren von Schachpartie-Aggregaten
 */
public class SchachpartieFactory {

    /**
     * Erzeugt eine Schachpartie mit einer (weltweit) eindeutigen ID
     * @return {@link SchachpartieExt}
     */
    public SchachpartieExt createSchachpartie() {
        return new SchachpartieExt(new SpielId());
    }

}
