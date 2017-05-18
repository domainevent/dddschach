package com.iks.dddschach.domain;

import com.iks.dddschach.domain.base.DomainService;

import java.util.List;
import java.util.Optional;


/**
 * Created by vollmer on 17.05.17.
 */
public class HalbzugValidation implements DomainService {

    public final class ValidationResult{}

    public ValidationResult validiere(
            Halbzug zuPruefen,
            List<Halbzug> zugHistorie,
            Optional<Spielbrett> aktSpielbrett) {

        return null;
    }

}
