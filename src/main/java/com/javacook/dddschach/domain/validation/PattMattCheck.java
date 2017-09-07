package com.javacook.dddschach.domain.validation;

import com.javacook.dddschach.domain.Farbe;
import com.javacook.dddschach.domain.Halbzug$;
import com.javacook.dddschach.domain.Position$;
import com.javacook.dddschach.domain.Spielbrett$;

import java.util.List;
import java.util.Objects;

import static com.javacook.dddschach.domain.validation.PattMattCheck.PattMatt.*;


/**
 * @author javacook
 */
public class PattMattCheck {

    private final static GesamtValidator GESAMT_VALIDATOR = new GesamtValidator();
    private final static SchachCheck SCHACH_CHECK = new SchachCheck();

    public enum PattMatt {
        PATT, MATT, NONE
    }

	/**
	 */
    public PattMatt analysiere(final List<Halbzug$> halbzugHistorie, final Spielbrett$ spielbrett) {
        Objects.requireNonNull(spielbrett, "Argument spielbrett is null");
        final Farbe spielerFarbe = ValidationUtils.spielerFarbe(halbzugHistorie.size());

        // Gehe all meine Figure durch und schauen, ob ich bei jedem gültigen Zug im Schach
        // steht
        for (Position$ meinePosition : spielbrett.allePositionenMitFarbe(spielerFarbe)) {
            for (Position$ moeglichesZiel : spielbrett.allePositionen()) {
                final Halbzug$ halbzug = new Halbzug$(meinePosition, moeglichesZiel);
                final HalbzugValidation.ValidationResult validationResult =
                        GESAMT_VALIDATOR.validiere(halbzug, halbzugHistorie, spielbrett);

                if (validationResult.gueltig) {
                    //  System.out.println(spielbrett);
                    //  System.out.println("Halbzug"+ halbzug);
                    return NONE;
                }
            }
        }
        final Position$ koenigsPosition = spielbrett.sucheKoenigsPosition(spielerFarbe);
        final Halbzug$ dummyHalbzug = new Halbzug$(koenigsPosition, koenigsPosition);
        return SCHACH_CHECK.validiere(dummyHalbzug, halbzugHistorie, spielbrett).gueltig?
                PATT : MATT;
    }

}
