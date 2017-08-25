package com.iks.dddschach.olddomain;

import com.iks.dddschach.olddomain.Position.Spalte;
import com.iks.dddschach.olddomain.Position.Zeile;

import static com.iks.dddschach.olddomain.Farbe.SCHWARZ;
import static com.iks.dddschach.olddomain.Farbe.WEISS;
import static com.iks.dddschach.olddomain.Position.Zeile.*;
import static com.iks.dddschach.olddomain.Position.Spalte.*;
import static com.iks.dddschach.olddomain.Spielfigur.FigurenTyp.*;


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

    
    public static Spielbrett decode(String schachbrettCompressed) {
        final Spielbrett spielbrett = new Spielbrett();
        int idx = 0;
        final char[] chars = schachbrettCompressed.toCharArray();
        for (Zeile z : Zeile.values()) {
            for (Spalte s : Spalte.values()) {
                switch (chars[idx++]) {
                    case 'R': spielbrett.setSchachfigurAnPosition(s, z, TURM, WEISS); break;
                    case 'N': spielbrett.setSchachfigurAnPosition(s, z, SPRINGER, WEISS); break;
                    case 'B': spielbrett.setSchachfigurAnPosition(s, z, LAEUFER, WEISS); break;
                    case 'Q': spielbrett.setSchachfigurAnPosition(s, z, DAME, WEISS); break;
                    case 'K': spielbrett.setSchachfigurAnPosition(s, z, KOENIG, WEISS); break;
                    case 'P': spielbrett.setSchachfigurAnPosition(s, z, BAUER, WEISS); break;
                    case 'r': spielbrett.setSchachfigurAnPosition(s, z, TURM, SCHWARZ); break;
                    case 'n': spielbrett.setSchachfigurAnPosition(s, z, SPRINGER, SCHWARZ); break;
                    case 'b': spielbrett.setSchachfigurAnPosition(s, z, LAEUFER, SCHWARZ); break;
                    case 'q': spielbrett.setSchachfigurAnPosition(s, z, DAME, SCHWARZ); break;
                    case 'k': spielbrett.setSchachfigurAnPosition(s, z, KOENIG, SCHWARZ); break;
                    case 'p': spielbrett.setSchachfigurAnPosition(s, z, BAUER, SCHWARZ); break;
                    case '_': break;
                    default:
                        throw new IllegalArgumentException(chars[idx++] + " is an invalid character");
                }
            }
        }
        return spielbrett;
    }

}