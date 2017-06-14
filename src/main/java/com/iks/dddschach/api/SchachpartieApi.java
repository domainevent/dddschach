package com.iks.dddschach.api;

import com.iks.dddschach.domain.Spielbrett;
import com.iks.dddschach.domain.SpielId;
import com.iks.dddschach.domain.Halbzug;
import com.iks.dddschach.domain.validation.Zugregel;

import java.io.IOException;
import java.text.ParseException;
import java.util.Optional;


/**
 * TODO: Gewuenscht ist, die Exceptions noch genauer zu klassifizieren
 */
public interface SchachpartieApi {

    /**
     * Erzeugt ein neues Spiel
     *
     * @param vermerk eine beliebiger Vermerk zu dieser Partie, z.B. Spieler, Ort, etc.
     * @return eine weltweit eindeutige Id
     * @throws Exception falls kein neues Spiel erzeugt werden konnte (technische Probleme)
     */
    SpielId neuesSpiel(Optional<String> vermerk) throws Exception;


    /**
     * Analysiert die Eingabe und erzeugt im Erfolgsfall einen Halbzug.
     * @param eingabe Die textuelle Halbzugeingabe, Beispiel: b1-c3
     * @return einen {@link Halbzug}
     * @throws ParseException
     */
    Halbzug parse(String eingabe) throws ParseException;


    /**
     * Soll erzeugt werden, falls die Spiel-Id nicht existiert.
     */
    @SuppressWarnings("serial")
	class UngueltigeSpielIdException extends Exception {
        public final SpielId spielId;

        public UngueltigeSpielIdException(SpielId spielId) {
            this.spielId = spielId;
        }
    }

    /**
     * Soll erzeugt werden, falls ein ungueltiger Zug ausgefuehrt worden ist.
     */
    @SuppressWarnings("serial")
	class UngueltigerHalbzugException extends Exception {
        public final Halbzug halbzug;
        public final Zugregel verletzteZugregel;


        public UngueltigerHalbzugException(Halbzug halbzug, Zugregel verletzteZugregel) {
            this.halbzug = halbzug;
            this.verletzteZugregel = verletzteZugregel;
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
     * @throws UngueltigeSpielIdException falls es keine Partie zu <code>spielId</code> gibt
     * @throws Exception bei sonstigen (technischen) Problemen
     */
    int fuehreHalbzugAus(SpielId spielId, Halbzug halbzug)
            throws UngueltigerHalbzugException, UngueltigeSpielIdException, IOException;


    /**
     * Liefert das aktuelle Schachbrett zum Spiel mit der Id <code>spielId</code>
     *
     * @param spielId (eindeutige) ID, die anfangs durch <code>neuesSpiel</code> erzeugt worden ist.
     * @return das aktuelle Schachbrett
     * @throws UngueltigeSpielIdException falls es keine Partie zu <code>spielId</code> gibt
     * @throws Exception bei sonstigen (technischen) Problemen
     */
    Spielbrett aktuellesSpielbrett(SpielId spielId) throws UngueltigeSpielIdException, IOException;

}
