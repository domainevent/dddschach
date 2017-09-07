package com.javacook.dddschach.persistence;

import com.javacook.dddschach.domain.HalbzugHistorie$;
import com.javacook.dddschach.domain.Schachpartie$;
import com.javacook.dddschach.domain.SpielId$;
import com.javacook.dddschach.domain.Spielbrett$;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.IOException;

import static com.javacook.dddschach.persistence.ObjectSerializer.objectToString;
import static com.javacook.dddschach.persistence.ObjectSerializer.stringToObject;


/**
 * Created by vollmer on 14.06.17.
 */
@Entity
class SchachpartieDB {

    @Id
    final String id;
    String spielbrett;
    String halbzugHistorie;

    public SchachpartieDB(Schachpartie$ schachpartie) throws IOException {
        id = schachpartie.getId().getId();
        spielbrett = objectToString(schachpartie.getSpielbrett());
        halbzugHistorie = objectToString(schachpartie.getHalbzugHistorie());
    }

    public Schachpartie$ toSchachpartie() throws IOException {
        final Spielbrett$ sb = stringToObject(this.spielbrett, Spielbrett$.class);
        final HalbzugHistorie$ hh = stringToObject(this.halbzugHistorie, HalbzugHistorie$.class);
        return new Schachpartie$(new SpielId$(id)) {{
            this.setSpielbrett(sb);
            this.setHalbzugHistorie(hh);
        }};
    }
}
