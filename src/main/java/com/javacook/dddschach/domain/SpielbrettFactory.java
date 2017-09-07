package com.javacook.dddschach.domain;


import static com.javacook.dddschach.domain.Farbe.SCHWARZ;
import static com.javacook.dddschach.domain.Farbe.WEISS;
import static com.javacook.dddschach.domain.FigurenTyp.*;
import static com.javacook.dddschach.domain.Spalte.*;


/**
 * Eine Fabrik, um Objekte des Typs Spielbrett herzustellen.
 */
public class SpielbrettFactory  {

    /**
     * Erzeugt ein initiales Schachbrett
     */
    public static Spielbrett$ createInitialesSpielbrett() {
        return new Spielbrett$() {{
            setSchachfigurAnPosition(A, Zeile.I, TURM, WEISS);
            setSchachfigurAnPosition(B, Zeile.I, SPRINGER, WEISS);
            setSchachfigurAnPosition(C, Zeile.I, LAEUFER, WEISS);
            setSchachfigurAnPosition(D, Zeile.I, DAME, WEISS);
            setSchachfigurAnPosition(E, Zeile.I, KOENIG, WEISS);
            setSchachfigurAnPosition(F, Zeile.I, LAEUFER, WEISS);
            setSchachfigurAnPosition(G, Zeile.I, SPRINGER, WEISS);
            setSchachfigurAnPosition(H, Zeile.I, TURM, WEISS);
            for (Spalte h : Spalte.values()) {
                setSchachfigurAnPosition(h, Zeile.II, BAUER, WEISS);
                setSchachfigurAnPosition(h, Zeile.VII, BAUER, SCHWARZ);
            }
            setSchachfigurAnPosition(A, Zeile.VIII, TURM, SCHWARZ);
            setSchachfigurAnPosition(B, Zeile.VIII, SPRINGER, SCHWARZ);
            setSchachfigurAnPosition(C, Zeile.VIII, LAEUFER, SCHWARZ);
            setSchachfigurAnPosition(D, Zeile.VIII, DAME, SCHWARZ);
            setSchachfigurAnPosition(E, Zeile.VIII, KOENIG, SCHWARZ);
            setSchachfigurAnPosition(F, Zeile.VIII, LAEUFER, SCHWARZ);
            setSchachfigurAnPosition(G, Zeile.VIII, SPRINGER, SCHWARZ);
            setSchachfigurAnPosition(H, Zeile.VIII, TURM, SCHWARZ);
        }};
    }

    
    public static Spielbrett$ decode(String schachbrettCompressed) {
        final Spielbrett$ spielbrett = new Spielbrett$();
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