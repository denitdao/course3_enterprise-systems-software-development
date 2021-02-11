package ua.kpi.tef.essd;

import ua.kpi.tef.essd.entity.Part;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Executor {

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ua.kpi.tef.essd.jpa");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();

        Part parts = new Part();
//        parts.setName("Test");

        entityManager.persist(parts);
        System.out.println("Resulting to " + entityManager.find(Part.class, 1));

        entityManager.getTransaction().commit();

        entityManager.close();
    }

}
