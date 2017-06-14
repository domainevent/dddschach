package com.iks.dddschach.persistence;

import com.iks.dddschach.domain.Schachpartie;
import com.iks.dddschach.domain.SchachpartieRepository;
import com.iks.dddschach.domain.SpielId;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


/**
 * Repository zum Speichern und Finden von Schachpartien
 */
public class SchachpartieRepositoryDB implements SchachpartieRepository {

    private Map<SpielId, Schachpartie> repository = new HashMap<>();

    public Optional<Schachpartie> findById(SpielId spielId) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/points.odb");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        final Schachpartie schachpartie = em.find(Schachpartie.class, spielId);
        em.getTransaction().commit();
        em.close();
        emf.close();
        return Optional.ofNullable(schachpartie);
    }

    public void save(Schachpartie schachpartie) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/points.odb");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(schachpartie);
        em.getTransaction().commit();
        em.close();
        emf.close();
    }

}
