package com.iks.dddschach.domain.validation;

import com.iks.dddschach.domain.Halbzug;
import com.iks.dddschach.domain.Halbzug$;
import com.iks.dddschach.domain.Spielbrett$;
import com.iks.dddschach.domain.Spielfigur;

import java.util.List;
import java.util.Objects;


/**
 * Hier wird ueberprueft, ob die Figur am Start des Halbzuges bzgl. ihrer individuelles Zugmoeglichkeit die
 * Zielposition erreichen kann. Dabei checken z.B. die eizelne Figuren-Regeln zusaetzlich, ob auf
 * der Bahn vom Start zum Ziel Figuren im Wege stehen (außer beim Springer).
 */
public class ErreicheZielCheck implements HalbzugValidation {

    public ValidationResult validiere(Halbzug$ halbzug, List<? extends Halbzug> halbzugHistorie, Spielbrett$ spielbrett) {
        Objects.requireNonNull(halbzug, "Argument halbzug is null");
        Objects.requireNonNull(spielbrett, "Argument spielbrett is null");

        Spielfigur zugFigur = spielbrett.getSchachfigurAnPosition(halbzug.getVon());
        Objects.requireNonNull(zugFigur, "There is no figure on " + halbzug.getNach());

        switch (zugFigur.getFigur()) {
            case BAUER:
                return new BauernRegel().validiere(halbzug, halbzugHistorie, spielbrett);
            case TURM:
                return new TurmRegel().validiere(halbzug, halbzugHistorie, spielbrett);
            case SPRINGER:
                return new SpringerRegel().validiere(halbzug, halbzugHistorie, spielbrett);
            case LAEUFER:
                return new LaeuferRegel().validiere(halbzug, halbzugHistorie, spielbrett);
            case DAME:
                return new DameRegel().validiere(halbzug, halbzugHistorie, spielbrett);
            case KOENIG:
                return new KoenigRegel().validiere(halbzug, halbzugHistorie, spielbrett);
            default:
                throw new IllegalStateException("Unexpected enum: " + zugFigur.getFigur());
        }
    }

}
