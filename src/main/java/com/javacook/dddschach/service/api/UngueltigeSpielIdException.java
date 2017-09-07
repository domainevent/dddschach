package com.javacook.dddschach.service.api;

import com.javacook.dddschach.domain.SpielId;


/**
 * Soll erzeugt werden, falls die Spiel-Id nicht existiert.
 */
public class UngueltigeSpielIdException extends Exception {
    public final SpielId spielId;

    public UngueltigeSpielIdException(SpielId spielId) {
        this.spielId = spielId;
    }
}
