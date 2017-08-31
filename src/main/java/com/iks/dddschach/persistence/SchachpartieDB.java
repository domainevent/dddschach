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

    public SchachpartieDB(Schachpartie$ schachpartie) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
//        mapper.enableDefaultTypingAsProperty(ObjectMapper.DefaultTyping.JAVA_LANG_OBJECT, "@class");
        mapper.addMixIn(Halbzug.class, Halbzug$.class);
        mapper.addMixIn(HalbzugHistorie.class, HalbzugHistorie$.class);
        mapper.addMixIn(Position.class, Position$.class);
        mapper.addMixIn(Schachpartie.class, Schachpartie$.class);
//        mapper.addMixIn(Spielbrett.class, Schachpartie$.class);
        mapper.addMixIn(Spielfeld.class, Spielfeld$.class);
        mapper.addMixIn(Spielfigur.class, Spielfigur$.class);
        mapper.addMixIn(SpielId.class, SpielId$.class);

        StringWriter sw1 = new StringWriter();
        id = schachpartie.getId().getId();
        mapper.writeValue(sw1, schachpartie.getSpielbrett());
        spielbrett = sw1.toString();
        StringWriter sw2 = new StringWriter();
        mapper.writeValue(sw2, schachpartie.getHalbzugHistorie());
        halbzugHistorie = sw2.toString();
    }

    public Schachpartie$ toSchachpartie() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
//        mapper.enableDefaultTypingAsProperty(ObjectMapper.DefaultTyping.JAVA_LANG_OBJECT, "@class");
        mapper.addMixIn(Halbzug.class, Halbzug$.class);
        mapper.addMixIn(HalbzugHistorie.class, HalbzugHistorie$.class);
        mapper.addMixIn(Position.class, Position$.class);
        mapper.addMixIn(Schachpartie.class, Schachpartie$.class);
//        mapper.addMixIn(Spielbrett.class, Schachpartie$.class);
        mapper.addMixIn(Spielfeld.class, Spielfeld$.class);
        mapper.addMixIn(Spielfigur.class, Spielfigur$.class);
        mapper.addMixIn(SpielId.class, SpielId$.class);

        final Spielbrett$ sb = mapper.readValue(this.spielbrett, Spielbrett$.class);
        final HalbzugHistorie$ hh = mapper.readValue(this.halbzugHistorie, HalbzugHistorie$.class);

        return new Schachpartie$(new SpielId$(id)) {{
            this.setSpielbrett(sb);
            this.setHalbzugHistorie(hh);
        }};
    }
}
