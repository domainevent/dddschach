package com.iks.dddschach.service.api;

import com.iks.dddschach.domain.Halbzug;
import com.iks.dddschach.domain.validation.Zugregel;

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
