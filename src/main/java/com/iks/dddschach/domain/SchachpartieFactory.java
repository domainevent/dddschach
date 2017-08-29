package com.iks.dddschach.domain;

import com.iks.dddschach.domain.SchachpartieExt;
import com.iks.dddschach.domain.SpielId;
import com.iks.dddschach.domain.SpielIdExt;


/**
 * Fabrik zum Kreieren von Schachpartie-Aggregaten
 */
public class SchachpartieFactory {

    /**
     * Erzeugt eine Schachpartie mit einer (weltweit) eindeutigen ID
     * @return {@link SchachpartieExt}
     */
    public SchachpartieExt createSchachpartie() {
        return new SchachpartieExt(new SpielIdExt());
    }

}
