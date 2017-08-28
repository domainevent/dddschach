package com.iks.dddschach.persistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iks.dddschach.domain.*;

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

    public SchachpartieDB(SchachpartieExt schachpartie) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        StringWriter sw1 = new StringWriter();
        id = schachpartie.getSpielId().getId();
        mapper.writeValue(sw1, schachpartie.aktuellesSpielbrett());
        spielbrett = sw1.toString();
        StringWriter sw2 = new StringWriter();
        mapper.writeValue(sw2, schachpartie.halbzugHistorie());
        halbzugHistorie = sw2.toString();
    }

    public SchachpartieExt toSchachpartie() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        final Spielbrett sb = mapper.readValue(this.spielbrett, Spielbrett.class);
        final HalbzugHistorie hh = mapper.readValue(this.halbzugHistorie, HalbzugHistorie.class);

        return new SchachpartieExt(new SpielId(id)) {{
            this.setSpielbrett(sb);
            this.setHalbzugHistorie(hh);
        }};
    }
}
