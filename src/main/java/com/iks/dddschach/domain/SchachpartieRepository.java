package com.iks.dddschach.domain;

import java.util.Optional;


/**
 * Repository zum Speichern und Finden von Schachpartien
 */
public interface SchachpartieRepository {

    Optional<Schachpartie> findById(SpielId spielId);

    void save(Schachpartie schachpartie);

}
