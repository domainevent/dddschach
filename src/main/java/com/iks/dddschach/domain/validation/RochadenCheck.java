package com.iks.dddschach.domain.validation;

import com.iks.dddschach.domain.Halbzug;
import com.iks.dddschach.domain.Position;
import com.iks.dddschach.domain.Spielbrett;
import com.iks.dddschach.domain.Spielfigur;
import com.iks.dddschach.domain.Spielfigur.FigurenTyp;

import java.util.*;
import java.util.stream.Collectors;

import static com.iks.dddschach.domain.Position.Spalte.*;
import static com.iks.dddschach.domain.Position.Zeile._1;
import static com.iks.dddschach.domain.Position.Zeile._8;


/**
 * Überprüft, ob der Königszug eine Rochade versucht, und ob dieser zulässig ist. Ein Rochaden-Versuch
 * ist dann gegeben, wenn der König von seiner Position aus zwei Felder nach links oder rechts bewegt
 * werden soll. In diesem Fall wird der Turmzug anschließend zusätzlich ausgeführt.
 * @see com.iks.dddschach.domain.Schachpartie#fuehreHalbzugAus
 */
public class RochadenCheck implements HalbzugValidation {

	final static FreieBahnCheck FREIE_BAHN_CHECK = new FreieBahnCheck();
	final static SchachCheck SCHACH_CHECK = new SchachCheck();

	final static Position UR_POSITION_KOENIG_WEISS        = new Position(E,_1);
    final static Position UR_POSITION_KOENIG_SCHWARZ      = new Position(E,_8);
    final static Position UR_POSITION_TURM_LINKS_WEISS    = new Position(A,_1);
    final static Position UR_POSITION_TURM_RECHTS_WEISS   = new Position(H,_1);
    final static Position UR_POSITION_TURM_LINKS_SCHWARZ  = new Position(A,_8);
    final static Position UR_POSITION_TURM_RECHTS_SCHWARZ = new Position(H,_8);

	final static Halbzug ROCHADE_HALBZUG_KOENIG_WEISS_GROSS   = new Halbzug(UR_POSITION_KOENIG_WEISS, new Position(C,_1));
    final static Halbzug ROCHADE_HALBZUG_KOENIG_WEISS_KLEIN   = new Halbzug(UR_POSITION_KOENIG_WEISS, new Position(G,_1));
    final static Halbzug ROCHADE_HALBZUG_KOENIG_SCHWARZ_GROSS = new Halbzug(UR_POSITION_KOENIG_SCHWARZ, new Position(C,_8));
    final static Halbzug ROCHADE_HALBZUG_KOENIG_SCHWARZ_KLEIN = new Halbzug(UR_POSITION_KOENIG_SCHWARZ, new Position(G,_8));

    final static Halbzug ROCHADE_HALBZUG_TURM_WEISS_GROSS   = new Halbzug(UR_POSITION_TURM_LINKS_WEISS, new Position(D,_1));
    final static Halbzug ROCHADE_HALBZUG_TURM_WEISS_KLEIN   = new Halbzug(UR_POSITION_TURM_RECHTS_WEISS, new Position(F,_1));
    final static Halbzug ROCHADE_HALBZUG_TURM_SCHWARZ_GROSS = new Halbzug(UR_POSITION_TURM_LINKS_SCHWARZ, new Position(D,_8));
    final static Halbzug ROCHADE_HALBZUG_TURM_SCHWARZ_KLEIN = new Halbzug(UR_POSITION_TURM_RECHTS_SCHWARZ, new Position(F,_8));

    final static Map<Halbzug, Halbzug> ZUGEHOERIGE_TURM_HALBZEUGE = new HashMap<Halbzug, Halbzug>() {{
        put(ROCHADE_HALBZUG_KOENIG_WEISS_GROSS,   ROCHADE_HALBZUG_TURM_WEISS_GROSS);
        put(ROCHADE_HALBZUG_KOENIG_WEISS_KLEIN,   ROCHADE_HALBZUG_TURM_WEISS_KLEIN);
        put(ROCHADE_HALBZUG_KOENIG_SCHWARZ_GROSS, ROCHADE_HALBZUG_TURM_SCHWARZ_GROSS);
        put(ROCHADE_HALBZUG_KOENIG_SCHWARZ_KLEIN, ROCHADE_HALBZUG_TURM_SCHWARZ_KLEIN);
    }};

    final static Set<Halbzug> GUELTIGE_ROCHADEN_HALBZEUGE = ZUGEHOERIGE_TURM_HALBZEUGE.keySet();


    public static class RochadenCheckResult extends ValidationResult {
        public final Halbzug turmHalbZug;

        public RochadenCheckResult(Zugregel verletzteZugregel) {
            super(false, verletzteZugregel);
            turmHalbZug = null;
        }

        public RochadenCheckResult(Halbzug turmHalbZug) {
            super(true, null);
            this.turmHalbZug = turmHalbZug;
        }
    }


	@Override
	public ValidationResult validiere(Halbzug halbzug, List<Halbzug> halbzugHistorie, Spielbrett spielbrett) {
        Objects.requireNonNull(halbzug, "Argument halbzug is null");
        Objects.requireNonNull(spielbrett, "Argument spielbrett is null");
        Objects.requireNonNull(halbzugHistorie, "Argument zugHistorie is null");

        Spielfigur zugFigur = spielbrett.getSchachfigurAnPosition(halbzug.von);
        Objects.requireNonNull(zugFigur, "There is no figure on " + halbzug.von);

        final Halbzug zugehoerigerTurmHalbzug = ZUGEHOERIGE_TURM_HALBZEUGE.get(halbzug);

        if (zugFigur.figurTyp != FigurenTyp.KOENIG) {
            return new RochadenCheckResult(Zugregel.HALBZUG_IST_KEINE_ROCHADE);
        }
        if (!GUELTIGE_ROCHADEN_HALBZEUGE.contains(halbzug)) {
            return new RochadenCheckResult(Zugregel.HALBZUG_IST_KEINE_ROCHADE);
        }

        // Freie Bahn vom König zum Turm checken:
        //
        final Halbzug halbzugVonKoenigZuTurm = new Halbzug(halbzug.von, zugehoerigerTurmHalbzug.von);
        final ValidationResult freieBahnCheckResult =
                FREIE_BAHN_CHECK.validiere(halbzugVonKoenigZuTurm, halbzugHistorie, spielbrett);

        if (!freieBahnCheckResult.gueltig) {
            return new RochadenCheckResult(freieBahnCheckResult.verletzteZugregel);
        }

        // Wurden König oder Turm schon einmal bewegt? Dazu die Startpositionen aller historischen
        // Halbzüge ermitteln.
        //
        final Set<Position> startPositionen = halbzugHistorie.stream().map(hz -> hz.von).collect(Collectors.toSet());

        // Hinweis: Dass auf Halbzug.von der König steht, wurde oben gecheckt:
        if (startPositionen.contains(halbzug.von)) {
            return new RochadenCheckResult(Zugregel.ROCHADE_KOENIG_WURDE_BEREITS_BEWEGT);
        }
        if (startPositionen.contains(zugehoerigerTurmHalbzug.von)) {
            return new RochadenCheckResult(Zugregel.ROCHADE_TURM_WURDE_BEREITS_BEWEGT);
        }

        // Ist ein Feld zwischen Start und Zielposition des Königs bedroht?
        //
        final Position midPos = ValidationUtils.middle(halbzug.von, halbzug.nach);

        boolean sindAllePositionenDesKoenigHalbzugsUnbedroht =
                SCHACH_CHECK.validiere(new Halbzug(halbzug.von, halbzug.von), halbzugHistorie, spielbrett).gueltig &&
                SCHACH_CHECK.validiere(new Halbzug(halbzug.von, midPos), halbzugHistorie, spielbrett).gueltig &&
                SCHACH_CHECK.validiere(halbzug, halbzugHistorie, spielbrett).gueltig;

        return sindAllePositionenDesKoenigHalbzugsUnbedroht?
                new RochadenCheckResult(zugehoerigerTurmHalbzug) :
                new RochadenCheckResult(Zugregel.ROCHADE_FELD_STEHT_IM_SCHACH);
    }

}
