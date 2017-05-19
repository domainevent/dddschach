package com.iks.dddschach.domain;

import com.iks.dddschach.domain.base.DomainService;

import java.util.List;


/**
 * Dieser Service dient dazu, eine Überprüfung eines Halbzuges auf Gültigkeit vorzunehemn.
 * Der Einfachheit halber, wird in dieser Implementierung lediglich getestet, ob sich überhaupt
 * eine Figur auf der Startposition des Halbzuges befindet und ob diese die richtig Farbe
 * hat - Die Spieler "Weiß" und "Schwarz" müssen sich abwechseln.
 */
public class HalbzugValidation implements DomainService {

    public final class ValidationResult {
        public final boolean valid;

        public ValidationResult(boolean valid) {
            this.valid = valid;
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
    public ValidationResult validiere(
            Halbzug zuPruefen,
            HalbzugHistorie zugHistorie,
            Spielbrett aktSpielbrett) {


        final Spielfigur schachfigurAnFrom = aktSpielbrett.getSchachfigurAnPosition(zuPruefen.from);
        if (schachfigurAnFrom == null) {
            return new ValidationResult(false);
        }

        if (schachfigurAnFrom.color.ordinal() != zugHistorie.size() % 2) {
            return new ValidationResult(false);
        }

        return new ValidationResult(true);
    }

}
