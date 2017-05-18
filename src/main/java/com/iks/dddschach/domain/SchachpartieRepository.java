package com.iks.dddschach.domain;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by vollmer on 18.05.17.
 */
public class SchachpartieRepository {

    Map<SpielId, Schachpartie> repository = new HashMap<>();

    public Schachpartie findById(SpielId spielId) {
        return repository.get(spielId);
    }

    public void save(Schachpartie schachpartie) {
        repository.put(schachpartie.getId(), schachpartie);
    }

}
