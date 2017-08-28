package com.iks.dddschach.persistence;

import com.iks.dddschach.domain.Schachpartie;
import com.iks.dddschach.domain.SchachpartieExt;
import com.iks.dddschach.domain.SpielId;
import com.iks.dddschach.olddomain.SchachpartieRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


/**
 * Repository zum Speichern und Finden von Schachpartien
 */
public class SchachpartieRepositoryMemory implements SchachpartieRepository {

    private final Map<SpielId, SchachpartieExt> repository = new HashMap<>();

    public Optional<SchachpartieExt> findById(SpielId spielId) {
        return Optional.ofNullable(repository.get(spielId));
    }

    public void save(SchachpartieExt schachpartie) {
        repository.put(schachpartie.getSpielId(), schachpartie);
    }

}
