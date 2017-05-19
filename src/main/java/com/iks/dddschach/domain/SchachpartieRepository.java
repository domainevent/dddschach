package com.iks.dddschach.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


/**
 * Created by vollmer on 18.05.17.
 */
public class SchachpartieRepository {

    private Map<SpielId, Schachpartie> repository = new HashMap<>();

    public Optional<Schachpartie> findById(SpielId spielId) {
        return Optional.ofNullable(repository.get(spielId));
    }

    public void save(Schachpartie schachpartie) {
        repository.put(schachpartie.getId(), schachpartie);
    }

}
