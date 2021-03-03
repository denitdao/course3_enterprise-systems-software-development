package ua.kpi.tef.essd.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public abstract class GenericDao<T> {
    private final Class<T> clazz;

    @PersistenceContext
    protected EntityManager entityManager;

    protected GenericDao(Class<T> clazz) {
        this.clazz = clazz;
    }

    public void save(T entity) {
        entityManager.persist(entity);
    }

    /**
     * Update and get synchronized instance
     */
    public T update(T entity) {
        return entityManager.merge(entity);
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
        entityManager.remove(entity);
    }

    public void deleteById(Integer id) {
        entityManager.remove(findById(id));
    }

    public boolean validate(Integer id) {
        return !entityManager
                .createQuery("select count(e) from " + clazz.getSimpleName() + " e where e.id = :id")
                .setParameter("id", id)
                .getSingleResult().equals(0L);
    }

}
