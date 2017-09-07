package com.javacook.dddschach.service.api;

import com.javacook.dddschach.domain.Halbzug;
import com.javacook.dddschach.domain.validation.Zugregel;


/**
 * Soll erzeugt werden, falls ein ungueltiger Zug ausgefuehrt worden ist.
 */
public class UngueltigerHalbzugException extends Exception {
    public final Halbzug halbzug;
    public final Zugregel verletzteZugregel;


    public UngueltigerHalbzugException(Halbzug halbzug, Zugregel verletzteZugregel) {
        this.halbzug = halbzug;
        this.verletzteZugregel = verletzteZugregel;
    }
}
