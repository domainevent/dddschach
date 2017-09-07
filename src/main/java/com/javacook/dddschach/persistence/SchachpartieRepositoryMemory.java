package com.javacook.dddschach.persistence;

import com.javacook.dddschach.domain.Schachpartie$;
import com.javacook.dddschach.domain.SpielId;
import com.javacook.dddschach.domain.SchachpartieRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


/**
 * Repository zum Speichern und Finden von Schachpartien
 */
public class SchachpartieRepositoryMemory implements SchachpartieRepository {

    private final Map<SpielId, Schachpartie$> repository = new HashMap<>();

    public Optional<Schachpartie$> findById(SpielId spielId) {
        return Optional.ofNullable(repository.get(spielId));
    }

    public void save(Schachpartie$ schachpartie) {
        repository.put(schachpartie.getId(), schachpartie);
    }

}
