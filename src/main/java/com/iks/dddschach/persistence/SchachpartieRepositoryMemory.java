package com.iks.dddschach.persistence;

import com.iks.dddschach.olddomain.Schachpartie;
import com.iks.dddschach.olddomain.SchachpartieRepository;
import com.iks.dddschach.olddomain.SpielId;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


/**
 * Repository zum Speichern und Finden von Schachpartien
 */
public class SchachpartieRepositoryMemory implements SchachpartieRepository {

    private final Map<SpielId, Schachpartie> repository = new HashMap<>();

    public Optional<Schachpartie> findById(SpielId spielId) {
        return Optional.ofNullable(repository.get(spielId));
    }

    public void save(Schachpartie schachpartie) {
        repository.put(schachpartie.getId(), schachpartie);
    }

}
