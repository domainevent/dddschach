package com.iks.dddschach.domain.validation;

import com.iks.dddschach.domain.Halbzug;
import com.iks.dddschach.domain.Spielbrett;

import java.util.List;


/**
 * Dieser Service dient dazu, eine Überprüfung eines Halbzuges auf Gültigkeit vorzunehemn.
 * Der Einfachheit halber, wird in dieser Implementierung lediglich getestet, ob sich überhaupt
 * eine Figur auf der Startposition des Halbzuges befindet und ob diese die richtig Farbe
 * hat: Die Spieler "Weiß" und "Schwarz" müssen sich abwechseln.
 */
public interface HalbzugValidation {

    class ValidationResult {
        public final boolean gueltig;
        public final Zugregel verletzteZugregel;

        public ValidationResult(boolean gueltig, Zugregel verletzteZugregel) {
            this.gueltig = false;
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
     * @param zugHistorie die Folge der bislang durchgeführten Halbzüge
     * @param aktSpielbrett das aktuelle Spielbrett mit den Information,
     *                      welche Figuren sich auf welchen Positionen befinden.
     * @return Das Validationsergebnis
     */
    ValidationResult validiere(Halbzug zuPruefen, List<Halbzug> zugHistorie, Spielbrett aktSpielbrett);


}
