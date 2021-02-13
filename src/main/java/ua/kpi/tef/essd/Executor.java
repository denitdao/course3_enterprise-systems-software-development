package ua.kpi.tef.essd;

import ua.kpi.tef.essd.entity.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Set;

public class Executor {

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ua.kpi.tef.essd.jpa");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        User user = new User("User 1", 19, null, null);
        entityManager.persist(user);

        Property property1 = new Property("Color", "Blue", null);
        Property property2 = new Property("Material", "Cotton", null);

        Part part1 = new Part("Part 1", Set.of(property1, property2));
        entityManager.persist(part1);

        Part part2 = new Part("Part 2", Set.of(property1));
        entityManager.persist(part2);

        Clothing clothing = new Clothing("Clothing 1", Type.MAN, Size.S, user);
        clothing.addPart(part1, 10);
        clothing.addPart(part2, 15);
        entityManager.persist(clothing);

        System.out.println("We get - " + entityManager.createQuery("from Clothing", Clothing.class).getResultList());
        System.out.println("We get - " + entityManager.createQuery("from Part", Part.class).getResultList());

        entityManager.getTransaction().commit();
        entityManager.close();
    }

}
