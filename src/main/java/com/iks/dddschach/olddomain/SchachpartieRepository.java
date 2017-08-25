package com.iks.dddschach.olddomain;

import java.io.IOException;
import java.util.Optional;


/**
 * Repository zum Speichern und Finden von Schachpartien
 */
public interface SchachpartieRepository {

    Optional<Schachpartie> findById(SpielId spielId) throws IOException;

    void save(Schachpartie schachpartie) throws IOException;

}
