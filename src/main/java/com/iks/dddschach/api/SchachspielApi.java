package com.iks.dddschach.api;

import com.iks.dddschach.domain.Schachbrett;
import com.iks.dddschach.domain.SpielId;
import com.iks.dddschach.domain.Halbzug;

import java.util.Optional;


/**
 * TODO: Gewuenscht ist, die Exceptions noch genauer zu klassifizieren
 */
public interface SchachspielApi {

    /**
     * Erzeugt ein neues Spiel
     *
     * @param vermerk eine beliebiger Vermerk zu dieser Partie, z.B. Spieler, Ort, etc.
     * @return eine weltweit eindeutige Id
     * @throws Exception falls keine neues Spiel erzeugt werden konnte (technische Probleme)
     */
    SpielId neuesSpiel(Optional<String> vermerk) throws Exception;


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
     * @throws Exception falls der Zug ungueltig ist oder das Spiel mit der Id <code>spielId</code>
     * nicht existiert
     */
    int fuehreHalbzugAus(SpielId spielId, Halbzug halbzug) throws Exception;


    /**
     * Liefert das aktuelle Schachbrett zum Spiel mit der Id <code>spielId</code>
     *
     * @param spielId (eindeutige) ID, die anfangs durch <code>neuesSpiel</code> erzeugt worden ist.
     * @return das aktuelle Schachbrett
     * @throws Exception falls as Spiel mit der Id <code>spielId</code> nicht existiert
     */
    Schachbrett schachBrett(SpielId spielId) throws Exception;

}
