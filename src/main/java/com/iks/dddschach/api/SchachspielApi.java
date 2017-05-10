package com.iks.dddschach.api;

import com.iks.dddschach.domain.Schachbrett;
import com.iks.dddschach.domain.SpielId;
import com.iks.dddschach.domain.Halbzug;


/**
 * TODO: Gewuenscht ist, die Exceptions noch genauer zu klassifizieren
 */
public interface SchachspielApi {

    /**
     * Erzeugt ein neues Spiel
     *
     * @return eine einedeutige ID
     * @throws Exception falls keine neues Spiel erzeugt werden konnte (technische Probleme)
     */
    SpielId neuesSpiel() throws Exception;

    /**
     * Fuehrt einen Halbzug in der Schachpartie mit der Id <code>spielId</code> aus
     *
     * @param spielId (eindeutige) ID, die anfangs durch <code>neuesSpiel</code> erzeugt worden ist.
     * @param zug
     * @return der Index des Zuges (beginnend mit 1)
     * @throws Exception falls der Zug ungueltig ist oder das Spiel mit der Id <code>spielId</code>
     * nicht existiert
     */
    int fuehreHalbzugAus(SpielId spielId, Halbzug zug) throws Exception;

    /**
     * Liefert das aktuelle Schachbrett zum Spiel mit der Id <code>spielId</code>
     *
     * @param spielId (eindeutige) ID, die anfangs durch <code>neuesSpiel</code> erzeugt worden ist.
     * @return das aktuelle Schachbrett
     * @throws Exception falls as Spiel mit der Id <code>spielId</code> nicht existiert
     */
    Schachbrett schachBrett(SpielId spielId) throws Exception;

}
