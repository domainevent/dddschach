package com.iks.dddschach.api;

import com.iks.dddschach.domain.Spielbrett;
import com.iks.dddschach.domain.SpielId;
import com.iks.dddschach.domain.Halbzug;

import java.util.Optional;


public interface SchachpartieApi {

    /**
     * Erzeugt ein neues Spiel
     *
     * @param vermerk eine beliebiger Vermerk zu dieser Partie, z.B. Spieler, Ort, etc.
     * @return eine weltweit eindeutige Id
     * @throws Exception falls kein neues Spiel erzeugt werden konnte (z.B. technische Probleme)
     */
    SpielId neuesSpiel(Optional<String> vermerk) throws Exception;


    /**
     * Muss erzeugt werden, falls ein ungueltiger Zug ausgefuehrt worden ist.
     * @see #fuehreHalbzugAus
     */
    class UngueltigerHalbzugException extends Exception {
        public final Halbzug halbzug;

        public UngueltigerHalbzugException(Halbzug halbzug) {
            this.halbzug = halbzug;
        }
    }

    /**
     * Fuehrt einen Halbzug in der Schachpartie mit der Id <code>spielId</code> aus. Der Halbzug
     * ist nur dann gültig, falls sich auf der Startpositon des Halbzuges eine Figur befindet
     * und falls diese Figur die korrekte Farbe hat. Schwarz und Weiß müssen sich abwechseln;
     * beginnend mit Weiß.
     * Befindet sich auf der Zielposition bereits eine Spielfigur, wird diese geschlagen.
     *
     * Hinweis: Eine komplette Validation nach den Schachregeln soll im Rahmen des DDD-Workshops
     * nicht implementiert werden.
     *
     * @param spielId (eindeutige) ID, die anfangs durch <code>neuesSpiel</code> erzeugt worden ist.
     * @param halbzug
     * @return der Index des Zuges (beginnend mit 1)
     * @throws UngueltigerHalbzugException falls der Halbzug ungueltig ist
     * @throws Exception falls das Spiel mit der Id <code>spielId</code> nicht existiert
     */
    int fuehreHalbzugAus(SpielId spielId, Halbzug halbzug) throws UngueltigerHalbzugException;


    /**
     * Liefert das aktuelle Schachbrett zum Spiel mit der Id <code>spielId</code>
     *
     * @param spielId (eindeutige) ID, die anfangs durch <code>neuesSpiel</code> erzeugt worden ist.
     * @return das aktuelle Schachbrett
     * @throws Exception falls das Spiel mit der Id <code>spielId</code> nicht existiert
     */
    Spielbrett spielbrett(SpielId spielId) throws Exception;

}
