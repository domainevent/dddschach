package com.javacook.dddschach.persistence;

import com.javacook.dddschach.domain.Schachpartie$;
import com.javacook.dddschach.domain.SpielId;
import com.javacook.dddschach.domain.SchachpartieRepository;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.io.IOException;
import java.util.Optional;


/**
 * Repository zum Speichern und Finden von Schachpartien
 */
public class SchachpartieRepositoryDB implements SchachpartieRepository {

    public final String DATABASE_NAME = "$objectdb/db/com.javacook.dddschach.odb";

    public Optional<Schachpartie$> findById(SpielId spielId) throws IOException {
        EntityManager em = Persistence.createEntityManagerFactory(DATABASE_NAME).createEntityManager();
        final SchachpartieDB schachpartieDB = em.find(SchachpartieDB.class, spielId.getId());
        em.close();
        return (schachpartieDB == null)? Optional.empty() : Optional.of(schachpartieDB.toSchachpartie());
    }

    public void save(Schachpartie$ schachpartie) throws IOException {
        EntityManager em = Persistence.createEntityManagerFactory(DATABASE_NAME).createEntityManager();
        em.getTransaction().begin();
        em.merge(new SchachpartieDB(schachpartie));
        em.getTransaction().commit();
        em.close();
    }

}
