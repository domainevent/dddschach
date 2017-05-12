package com.iks.dddschach.util;

import com.iks.dddschach.domain.Position.HorCoord;
import com.iks.dddschach.domain.Schachbrett;

import static com.iks.dddschach.domain.FarbeEnum.SCHWARZ;
import static com.iks.dddschach.domain.FarbeEnum.WEISS;
import static com.iks.dddschach.domain.Position.HorCoord.*;
import static com.iks.dddschach.domain.Position.VertCoord.*;
import static com.iks.dddschach.domain.Spielfigur.FigureEnum.*;

public class SampleDataFactory {

    /**
     * Erzeugt ein initiales Schachbrett
     */
    public static Schachbrett createInitialesSchachbrett() {

        return new Schachbrett() {{
            setSchachfigurAnPosition(A, _1, TURM, WEISS);
            setSchachfigurAnPosition(B, _1, PFERD, WEISS);
            setSchachfigurAnPosition(C, _1, LAEUFER, WEISS);
            setSchachfigurAnPosition(D, _1, DAME, WEISS);
            setSchachfigurAnPosition(E, _1, KOENIG, WEISS);
            setSchachfigurAnPosition(F, _1, LAEUFER, WEISS);
            setSchachfigurAnPosition(G, _1, PFERD, WEISS);
            setSchachfigurAnPosition(H, _1, TURM, WEISS);
            for (HorCoord h : HorCoord.values()) {
                setSchachfigurAnPosition(h, _2, BAUER, WEISS);
                setSchachfigurAnPosition(h, _7, BAUER, SCHWARZ);
            }
            setSchachfigurAnPosition(A, _8, TURM, SCHWARZ);
            setSchachfigurAnPosition(B, _8, PFERD, SCHWARZ);
            setSchachfigurAnPosition(C, _8, LAEUFER, SCHWARZ);
            setSchachfigurAnPosition(D, _8, DAME, SCHWARZ);
            setSchachfigurAnPosition(E, _8, KOENIG, SCHWARZ);
            setSchachfigurAnPosition(F, _8, LAEUFER, SCHWARZ);
            setSchachfigurAnPosition(G, _8, PFERD, SCHWARZ);
            setSchachfigurAnPosition(H, _8, TURM, SCHWARZ);
        }};
    }

}
