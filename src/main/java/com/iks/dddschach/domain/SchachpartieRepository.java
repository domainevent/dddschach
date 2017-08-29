package com.iks.dddschach.domain;

import com.iks.dddschach.domain.SchachpartieExt;
import com.iks.dddschach.domain.SpielId;

import java.io.IOException;
import java.util.Optional;


/**
 * Repository zum Speichern und Finden von Schachpartien
 */
public interface SchachpartieRepository {

    Optional<SchachpartieExt> findById(SpielId spielId) throws IOException;

    void save(SchachpartieExt schachpartie) throws IOException;

}
