package ua.kpi.tef.essd.dao;

import ua.kpi.tef.essd.entity.ClothesSet;

import java.util.List;

public class ClothesSetDao extends GenericDao<ClothesSet> {

    public ClothesSetDao() {
        super(ClothesSet.class);
    }

    public List<ClothesSet> findByName(String name) {
        return entityManager
                .createQuery("select cs from ClothesSet cs where cs.name like :name", ClothesSet.class)
                .setParameter("name", name+'%')
                .getResultList();
    }
}
