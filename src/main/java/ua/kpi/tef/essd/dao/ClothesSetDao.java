package ua.kpi.tef.essd.dao;

import org.springframework.stereotype.Component;
import ua.kpi.tef.essd.entity.ClothesSet;

import java.util.List;

@Component
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

/*    public List<ClothesSet> findClothesSetsByUserId(Integer id) {
        return entityManager
                .createQuery("select cs from ClothesSet cs where cs.user.id = :id", ClothesSet.class)
                .setParameter("id", id)
                .getResultList();
    }*/
}
