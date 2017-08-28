package com.iks.dddschach.persistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iks.dddschach.olddomain.HalbzugHistorie;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.IOException;
import java.io.StringWriter;


/**
 * Created by vollmer on 14.06.17.
 */
@Entity
class SchachpartieDB {

    @Id
    final String id;
    String spielbrett;
    String halbzugHistorie;

    public SchachpartieDB(Schachpartie schachpartie) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        StringWriter sw1 = new StringWriter();
        id = schachpartie.getId().id;
        mapper.writeValue(sw1, schachpartie.aktuellesSpielbrett());
        spielbrett = sw1.toString();
        StringWriter sw2 = new StringWriter();
        mapper.writeValue(sw2, schachpartie.halbzugHistorie());
        halbzugHistorie = sw2.toString();
    }

    public Schachpartie toSchachpartie() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        final Spielbrett sb = mapper.readValue(this.spielbrett, Spielbrett.class);
        final HalbzugHistorie hh = mapper.readValue(this.halbzugHistorie, HalbzugHistorie.class);

        return new Schachpartie(new SpielId(id)) {{
            this.spielbrett = sb;
            this.halbzugHistorie = hh;
        }};
    }
}
