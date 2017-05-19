package com.iks.dddschach.domain;

import com.iks.dddschach.domain.base.DomainService;


/**
 * Dieser Service dient dazu, eine Überprüfung eines Halbzuges auf Gültigkeit vorzunehemn.
 * Der Einfachheit halber, wird in dieser Implementierung lediglich getestet, ob sich überhaupt
 * eine Figur auf der Startposition des Halbzuges befindet und ob diese die richtig Farbe
 * hat - Die Spieler "Weiß" und "Schwarz" müssen sich abwechseln.
 */
public class HalbzugValidation implements DomainService {

    public final class ValidationResult {
        public boolean valid;

        public ValidationResult(boolean valid) {
            this.valid = valid;
        }
    }


    /**
     *
     * @param zuPruefen
     * @param zugHistorie
     * @param aktSpielbrett
     * @return
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
