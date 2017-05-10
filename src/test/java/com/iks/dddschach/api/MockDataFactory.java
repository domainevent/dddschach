package com.iks.dddschach.api;

import com.iks.dddschach.domain.PositionValueObject.HorCoord;
import com.iks.dddschach.domain.SchachbrettValueObject;

import static com.iks.dddschach.domain.FarbeEnum.SCHWARZ;
import static com.iks.dddschach.domain.FarbeEnum.WEISS;
import static com.iks.dddschach.domain.PositionValueObject.HorCoord.*;
import static com.iks.dddschach.domain.PositionValueObject.VertCoord.*;
import static com.iks.dddschach.domain.SpielfigurValueObject.FigureEnum.*;


/**
 * Created by vollmer on 09.05.17.
 */
public class MockDataFactory {

    public static SchachbrettValueObject createInitialesSchachbrett() {
        return new SchachbrettValueObject() {{
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


    public static void main(String[] args) {
        final SchachbrettValueObject initialesSchachbrett1 = createInitialesSchachbrett();
        final SchachbrettValueObject initialesSchachbrett2 = createInitialesSchachbrett();

        System.out.println(initialesSchachbrett1.equals(initialesSchachbrett2));
    }

}
