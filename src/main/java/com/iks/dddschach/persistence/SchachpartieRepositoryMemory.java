package com.iks.dddschach.persistence;

import com.iks.dddschach.domain.Schachpartie;
import com.iks.dddschach.domain.SchachpartieRepository;
import com.iks.dddschach.domain.SpielId;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


/**
 * Repository zum Speichern und Finden von Schachpartien
 */
public class SchachpartieRepositoryMemory implements SchachpartieRepository {

    private Map<SpielId, Schachpartie> repository = new HashMap<>();

    public Optional<Schachpartie> findById(SpielId spielId) {
        return Optional.ofNullable(repository.get(spielId));
    }

    public void save(Schachpartie schachpartie) {
        repository.put(schachpartie.getId(), schachpartie);
    }

}
