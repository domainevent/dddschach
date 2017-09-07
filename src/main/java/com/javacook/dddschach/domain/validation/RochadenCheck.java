package com.javacook.dddschach.domain.validation;


import com.javacook.dddschach.domain.*;

import java.util.*;
import java.util.stream.Collectors;

import static com.javacook.dddschach.domain.Spalte.*;
import static com.javacook.dddschach.domain.Zeile.I;
import static com.javacook.dddschach.domain.Zeile.VIII;


/**
 * Überprüft, ob der Königszug eine Rochade versucht, und ob dieser zulässig ist. Ein Rochaden-Versuch
 * ist dann gegeben, wenn der König von seiner Position aus zwei Felder nach links oder rechts bewegt
 * werden soll. In diesem Fall wird der Turmzug anschließend zusätzlich ausgeführt.
 */
public class RochadenCheck implements HalbzugValidation {

	final static FreieBahnCheck FREIE_BAHN_CHECK = new FreieBahnCheck();
	final static SchachCheck SCHACH_CHECK = new SchachCheck();

	final static Position$ UR_POSITION_KOENIG_WEISS        = new Position$(E, I);
    final static Position$ UR_POSITION_KOENIG_SCHWARZ      = new Position$(E, VIII);
    final static Position$ UR_POSITION_TURM_LINKS_WEISS    = new Position$(A, I);
    final static Position$ UR_POSITION_TURM_RECHTS_WEISS   = new Position$(H, I);
    final static Position$ UR_POSITION_TURM_LINKS_SCHWARZ  = new Position$(A, VIII);
    final static Position$ UR_POSITION_TURM_RECHTS_SCHWARZ = new Position$(H, VIII);

	final static Halbzug$ ROCHADE_HALBZUG_KOENIG_WEISS_GROSS   = new Halbzug$(UR_POSITION_KOENIG_WEISS, new Position$(C,I));
    final static Halbzug$ ROCHADE_HALBZUG_KOENIG_WEISS_KLEIN   = new Halbzug$(UR_POSITION_KOENIG_WEISS, new Position$(G,I));
    final static Halbzug$ ROCHADE_HALBZUG_KOENIG_SCHWARZ_GROSS = new Halbzug$(UR_POSITION_KOENIG_SCHWARZ, new Position$(C,VIII));
    final static Halbzug$ ROCHADE_HALBZUG_KOENIG_SCHWARZ_KLEIN = new Halbzug$(UR_POSITION_KOENIG_SCHWARZ, new Position$(G,VIII));

    final static Halbzug$ ROCHADE_HALBZUG_TURM_WEISS_GROSS   = new Halbzug$(UR_POSITION_TURM_LINKS_WEISS, new Position$(D,I));
    final static Halbzug$ ROCHADE_HALBZUG_TURM_WEISS_KLEIN   = new Halbzug$(UR_POSITION_TURM_RECHTS_WEISS, new Position$(F,I));
    final static Halbzug$ ROCHADE_HALBZUG_TURM_SCHWARZ_GROSS = new Halbzug$(UR_POSITION_TURM_LINKS_SCHWARZ, new Position$(D,VIII));
    final static Halbzug$ ROCHADE_HALBZUG_TURM_SCHWARZ_KLEIN = new Halbzug$(UR_POSITION_TURM_RECHTS_SCHWARZ, new Position$(F,VIII));

    final static Map<Halbzug$, Halbzug$> ZUGEHOERIGE_TURM_HALBZEUGE = new HashMap<Halbzug$, Halbzug$>() {{
        put(ROCHADE_HALBZUG_KOENIG_WEISS_GROSS,   ROCHADE_HALBZUG_TURM_WEISS_GROSS);
        put(ROCHADE_HALBZUG_KOENIG_WEISS_KLEIN,   ROCHADE_HALBZUG_TURM_WEISS_KLEIN);
        put(ROCHADE_HALBZUG_KOENIG_SCHWARZ_GROSS, ROCHADE_HALBZUG_TURM_SCHWARZ_GROSS);
        put(ROCHADE_HALBZUG_KOENIG_SCHWARZ_KLEIN, ROCHADE_HALBZUG_TURM_SCHWARZ_KLEIN);
    }};

    final static Set<Halbzug$> GUELTIGE_ROCHADEN_HALBZEUGE = ZUGEHOERIGE_TURM_HALBZEUGE.keySet();


    public static class RochadenCheckResult extends ValidationResult {
        public final Halbzug$ turmHalbZug;

        public RochadenCheckResult(Zugregel verletzteZugregel) {
            super(false, verletzteZugregel);
            turmHalbZug = null;
        }

        public RochadenCheckResult(Halbzug$ turmHalbZug) {
            super(true, null);
            this.turmHalbZug = turmHalbZug;
        }
    }


	@Override
	public ValidationResult validiere(Halbzug$ halbzug, List<Halbzug$> halbzugHistorie, Spielbrett$ spielbrett) {
        Objects.requireNonNull(halbzug, "Argument halbzug is null");
        Objects.requireNonNull(spielbrett, "Argument spielbrett is null");
        Objects.requireNonNull(halbzugHistorie, "Argument zugHistorie is null");

        Spielfigur$ zugFigur = spielbrett.getSchachfigurAnPosition(halbzug.getVon());
        Objects.requireNonNull(zugFigur, "There is no figure on " + halbzug.getVon());

        final Halbzug$ zugehoerigerTurmHalbzug = ZUGEHOERIGE_TURM_HALBZEUGE.get(halbzug);

        if (zugFigur.getFigur() != FigurenTyp.KOENIG) {
            return new RochadenCheckResult(Zugregel.HALBZUG_IST_KEINE_ROCHADE);
        }
        if (!GUELTIGE_ROCHADEN_HALBZEUGE.contains(halbzug)) {
            return new RochadenCheckResult(Zugregel.HALBZUG_IST_KEINE_ROCHADE);
        }

        // Freie Bahn vom König zum Turm checken:
        //
        final Halbzug$ halbzugVonKoenigZuTurm = new Halbzug$(halbzug.getVon(), zugehoerigerTurmHalbzug.getVon());
        final ValidationResult freieBahnCheckResult =
                FREIE_BAHN_CHECK.validiere(halbzugVonKoenigZuTurm, halbzugHistorie, spielbrett);

        if (!freieBahnCheckResult.gueltig) {
            return new RochadenCheckResult(freieBahnCheckResult.verletzteZugregel);
        }

        // Wurden König oder Turm schon einmal bewegt? Dazu die Startpositionen aller historischen
        // Halbzüge ermitteln.
        //
        final Set<Position> startPositionen = halbzugHistorie.stream().map(hz -> hz.getVon()).collect(Collectors.toSet());

        // Hinweis: Dass auf Halbzug.from der König steht, wurde oben gecheckt:
        if (startPositionen.contains(halbzug.getVon())) {
            return new RochadenCheckResult(Zugregel.ROCHADE_KOENIG_WURDE_BEREITS_BEWEGT);
        }
        if (startPositionen.contains(zugehoerigerTurmHalbzug.getVon())) {
            return new RochadenCheckResult(Zugregel.ROCHADE_TURM_WURDE_BEREITS_BEWEGT);
        }

        // Ist ein Feld zwischen Start und Zielposition des Königs bedroht?
        //
        final Position$ midPos = ValidationUtils.middle(halbzug.getVon(), halbzug.getNach());

        boolean sindAllePositionenDesKoenigHalbzugsUnbedroht =
                SCHACH_CHECK.validiere(new Halbzug$(halbzug.getVon(), halbzug.getVon()), halbzugHistorie, spielbrett).gueltig &&
                SCHACH_CHECK.validiere(new Halbzug$(halbzug.getVon(), midPos), halbzugHistorie, spielbrett).gueltig &&
                SCHACH_CHECK.validiere(halbzug, halbzugHistorie, spielbrett).gueltig;

        return sindAllePositionenDesKoenigHalbzugsUnbedroht?
                new RochadenCheckResult(zugehoerigerTurmHalbzug) :
                new RochadenCheckResult(Zugregel.ROCHADE_FELD_STEHT_IM_SCHACH);
    }

}
