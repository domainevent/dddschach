package com.iks.dddschach.domain;

import com.iks.dddschach.domain.Position.Spalte;

import static com.iks.dddschach.domain.Farbe.SCHWARZ;
import static com.iks.dddschach.domain.Farbe.WEISS;
import static com.iks.dddschach.domain.Position.Zeile.*;
import static com.iks.dddschach.domain.Position.Spalte.*;
import static com.iks.dddschach.domain.Spielfigur.FigurenTyp.*;


/**
 * Eine Fabrik, um Objekte des Typs Spielbrett herzustellen.
 */
public class SpielbrettFactory {

    /**
     * Erzeugt ein initiales Schachbrett
     */
    public static Spielbrett createInitialesSpielbrett() {

        return new Spielbrett() {{
            setSchachfigurAnPosition(A, _1, TURM, WEISS);
            setSchachfigurAnPosition(B, _1, SPRINGER, WEISS);
            setSchachfigurAnPosition(C, _1, LAEUFER, WEISS);
            setSchachfigurAnPosition(D, _1, DAME, WEISS);
            setSchachfigurAnPosition(E, _1, KOENIG, WEISS);
            setSchachfigurAnPosition(F, _1, LAEUFER, WEISS);
            setSchachfigurAnPosition(G, _1, SPRINGER, WEISS);
            setSchachfigurAnPosition(H, _1, TURM, WEISS);
            for (Spalte h : Spalte.values()) {
                setSchachfigurAnPosition(h, _2, BAUER, WEISS);
                setSchachfigurAnPosition(h, _7, BAUER, SCHWARZ);
            }
            setSchachfigurAnPosition(A, _8, TURM, SCHWARZ);
            setSchachfigurAnPosition(B, _8, SPRINGER, SCHWARZ);
            setSchachfigurAnPosition(C, _8, LAEUFER, SCHWARZ);
            setSchachfigurAnPosition(D, _8, DAME, SCHWARZ);
            setSchachfigurAnPosition(E, _8, KOENIG, SCHWARZ);
            setSchachfigurAnPosition(F, _8, LAEUFER, SCHWARZ);
            setSchachfigurAnPosition(G, _8, SPRINGER, SCHWARZ);
            setSchachfigurAnPosition(H, _8, TURM, SCHWARZ);
        }};
    }

}
