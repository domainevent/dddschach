package com.iks.dddschach.persistence;

import com.iks.dddschach.domain.Schachpartie;
import com.iks.dddschach.domain.SchachpartieRepository;
import com.iks.dddschach.domain.SpielId;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.util.Optional;


/**
 * Repository zum Speichern und Finden von Schachpartien
 */
public class SchachpartieRepositoryDB implements SchachpartieRepository {

    public final String DATABASE_NAME = "$objectdb/db/dddschach.odb";

    public Optional<Schachpartie> findById(SpielId spielId) throws IOException {
        EntityManager em = Persistence.createEntityManagerFactory(DATABASE_NAME).createEntityManager();
        final SchachpartieDB schachpartieDB = em.find(SchachpartieDB.class, spielId.id);
        em.close();
        return (schachpartieDB == null)? Optional.empty() : Optional.of(schachpartieDB.toSchachpartie());
    }

    public void save(Schachpartie schachpartie) throws IOException {
        EntityManager em = Persistence.createEntityManagerFactory(DATABASE_NAME).createEntityManager();
        em.getTransaction().begin();
        em.merge(new SchachpartieDB(schachpartie));
        em.getTransaction().commit();
        em.close();
    }

}
