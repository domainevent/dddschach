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

    public final class ValidationResult {}


    /**
     *
     * @param zuPruefen
     * @param zugHistorie
     * @param aktSpielbrett
     * @return
     */
    public ValidationResult validiere(
            Halbzug zuPruefen,
            List<Halbzug> zugHistorie,
            Spielbrett aktSpielbrett) {

        return null;
    }

}
