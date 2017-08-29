package com.iks.dddschach.domain.validation;

import com.iks.dddschach.domain.*;

import java.util.List;
import java.util.Objects;

import static com.iks.dddschach.domain.Farbe.SCHWARZ;
import static com.iks.dddschach.domain.Farbe.WEISS;
import static com.iks.dddschach.domain.validation.ValidationUtils.spielerFarbe;


/**
 * Checkt, ob sich der ziehende Spieler nach seinem Halbzug (noch) im Schach befindet
 * @author javacook
 */
public class SchachCheck implements HalbzugValidation {

    private final static ErreicheZielCheck ERREICHE_ZIEL_CHECK = new ErreicheZielCheck();

	/**
	 * Checkt, ob der Spieler, wenn er den Halbzug <code>halbzug</code> zöge, danach noch
     * im Schach stünde. Dann dürfte er diesen Halbzug nicht ausführen.
	 */
    @Override
    public ValidationResult validiere(final Halbzug$ halbzug,
                                      final List<? extends Halbzug> halbzugHistorie,
                                      final Spielbrett$ spielbrett) {

        Objects.requireNonNull(halbzug, "Argument halbzug is null");
        Objects.requireNonNull(spielbrett, "Argument spielbrett is null");

        final Farbe spielerFarbe = spielerFarbe(halbzug, spielbrett);
        Farbe gegnerFarbe = spielerFarbe == WEISS? SCHWARZ : WEISS;
        final Spielbrett$ brettMitHalbzug = spielbrett.wendeHalbzugAn(halbzug);
        final Position koenigsPosition = brettMitHalbzug.sucheKoenigsPosition(spielerFarbe);

        // Gehe alle Figuren des Gegners durch und prüfe, ob diese meinen König schlagen könnten:
        for (Position$ lfdPos : brettMitHalbzug.getPositionenMitFarbe(gegnerFarbe)) {
            final Halbzug$ lfdHalbzug = new Halbzug$(lfdPos, koenigsPosition);
            if (istZielDesHalbzugsBedroht(lfdHalbzug, halbzugHistorie, brettMitHalbzug)) {
                return new ValidationResult(Zugregel.KOENIG_STEHT_IM_SCHACH);
            }
        }
        return new ValidationResult();
    }

    private boolean istZielDesHalbzugsBedroht(final Halbzug$ halbzug,
                                              final List<? extends Halbzug> halbzugHistorie,
                                              final Spielbrett$ spielbrett) {
        return ERREICHE_ZIEL_CHECK.validiere(halbzug, halbzugHistorie, spielbrett).gueltig;
    }

}
