package com.iks.dddschach.domain.validation;

import com.iks.dddschach.domain.Halbzug;
import com.iks.dddschach.domain.Spielbrett;
import com.iks.dddschach.domain.Spielfigur;
import com.iks.dddschach.domain.base.DomainService;

import java.util.List;
import java.util.Objects;

import static com.iks.dddschach.domain.validation.Zugregel.DER_RICHTIGE_SPIELER_MUSS_AM_ZUG_SEIN;
import static com.iks.dddschach.domain.validation.Zugregel.STARTFELD_MUSS_SPIELFIGUR_ENTHALTEN;


/**
 * Hier wird ueberprueft, ob die Figur am Start des Halbzuges bzgl. ihrer individuelles Zugmoeglichkeit die
 * Zielposition erreichen kann. Dabei checken z.B. die eizelne Figuren-Regeln zusaetzlich, ob auf
 * der Bahn vom Start zum Ziel Figuren im Wege stehen (außer beim Springer).
 */
public class ErreicheZielPruefung implements HalbzugValidation {

    public ValidationResult validiere(Halbzug halbzug, List<Halbzug> zugHistorie, Spielbrett spielbrett) {
        Objects.requireNonNull(halbzug, "Argument halbzug is null");
        Objects.requireNonNull(spielbrett, "Argument spielbrett is null");

        Spielfigur zugFigur = spielbrett.getSchachfigurAnPosition(halbzug.from);
        Objects.requireNonNull(zugFigur, "There is no figure on " + halbzug.from);

        switch (zugFigur.figure) {
            case BAUER:
                return new BauernRegel().validiere(halbzug, zugHistorie, spielbrett);
            case TURM:
                return new TurmRegel().validiere(halbzug, zugHistorie, spielbrett);
            case SPRINGER:
                return new SpringerRegel().validiere(halbzug, zugHistorie, spielbrett);
            case LAEUFER:
                return new LaeuferRegel().validiere(halbzug, zugHistorie, spielbrett);
            case DAME:
                return new DameRegel().validiere(halbzug, zugHistorie, spielbrett);
            case KOENIG:
                return new KoenigRegel().validiere(halbzug, zugHistorie, spielbrett);
            default:
                throw new IllegalStateException("Unexpected enum: " + zugFigur.figure);
        }
    }

}
