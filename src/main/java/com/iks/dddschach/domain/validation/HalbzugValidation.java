package com.iks.dddschach.domain.validation;

import com.iks.dddschach.domain.Halbzug;
import com.iks.dddschach.domain.Spielbrett$;

import java.util.List;


/**
 *
 */
public interface HalbzugValidation {

    /**
     * Ergebnis einer Halbzugvalidation
     */
    class ValidationResult {
        public final boolean gueltig;
        public final Zugregel verletzteZugregel;

        public ValidationResult(boolean gueltig, Zugregel verletzteZugregel) {
            this.gueltig = gueltig;
            this.verletzteZugregel = verletzteZugregel;
        }

        public ValidationResult(Zugregel verletzteZugregel) {
            this(false, verletzteZugregel);
        }

        public ValidationResult() {
            this(true, null);
        }
    }

    /**
     * Führt die Gültigkeitsüberprüfung aus
     * Bemerkung: Der Parameter <code>aktSpielbrett</code> ist eigentlich redundant, da
     * sich das aktuelle Spielfeld stets aus <code>zugHistorie</code> berechnen lässt.
     * @param zuPruefen der zu prüfende Halbzug
     * @param halbzugHistorie die Folge der bislang durchgeführten Halbzüge
     * @param aktSpielbrett das aktuelle Spielbrett mit den Information,
     *                      welche Figuren sich auf welchen Positionen befinden.
     * @return Das Validationsergebnis
     */
    ValidationResult validiere(Halbzug zuPruefen, List<Halbzug> halbzugHistorie, Spielbrett$ aktSpielbrett);

}
