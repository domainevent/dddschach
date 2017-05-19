package com.iks.dddschach.domain;

import com.iks.dddschach.domain.base.DomainService;


/**
 * Created by vollmer on 17.05.17.
 */
public class HalbzugValidation implements DomainService {

    public final class ValidationResult {
        public boolean valid;

        public ValidationResult(boolean valid) {
            this.valid = valid;
        }
    }

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
