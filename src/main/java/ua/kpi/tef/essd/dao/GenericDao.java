package ua.kpi.tef.essd.dao;

import ua.kpi.tef.essd.util.EntityManagerCreator;

import javax.persistence.EntityManager;
import java.util.List;

public abstract class GenericDao<T> {
    private final Class<T> clazz;
    protected EntityManager entityManager;

    protected GenericDao(Class<T> clazz) {
        this.clazz = clazz;
        entityManager = EntityManagerCreator.getEntityManager();
    }

    public void save(T entity) {
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
    }

    /**
     * Update and get synchronized instance
     */
    public T update(T entity) {
        entityManager.getTransaction().begin();
        T merged = entityManager.merge(entity);
        entityManager.getTransaction().commit();
        return merged;
    }

    public T findById(Integer id) {
        return entityManager.find(clazz, id);
    }

    public List<T> findAll() {
        return entityManager
                .createQuery("from " + clazz.getSimpleName(), clazz)
                .getResultList();
    }

    public void delete(T entity) {
        entityManager.getTransaction().begin();
        entityManager.remove(entity);
        entityManager.getTransaction().commit();
    }

    public void deleteById(Integer id) {
        entityManager.getTransaction().begin();
        entityManager.remove(findById(id));
        entityManager.getTransaction().commit();
    }

}
