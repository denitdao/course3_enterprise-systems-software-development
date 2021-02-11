package ua.kpi.tef.essd.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ClothesDao {
    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ua.kpi.tef.essd.jpa");
    private EntityManager entityManager;

    public void doSomething() {
        entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        // do some stuff
        entityManager.getTransaction().commit();
    }
}
