package ua.kpi.tef.essd.dao;

import org.springframework.stereotype.Component;
import ua.kpi.tef.essd.entity.Clothing;
import ua.kpi.tef.essd.entity.Size;
import ua.kpi.tef.essd.entity.Type;

import java.util.List;

@Component
public class ClothingDao extends GenericDao<Clothing> {

    public ClothingDao() {
        super(Clothing.class);
    }

    public List<Clothing> findByName(String name) {
        return entityManager
                .createQuery("select c from Clothing c where c.name like :name", Clothing.class)
                .setParameter("name", name+'%')
                .getResultList();
    }

    public List<Clothing> findBySize(Size size) {
        return entityManager
                .createQuery("select c from Clothing c where c.size = :size", Clothing.class)
                .setParameter("size", size)
                .getResultList();
    }

    public List<Clothing> findByType(Type type) {
        return entityManager
                .createQuery("select c from Clothing c where c.type = :type", Clothing.class)
                .setParameter("type", type)
                .getResultList();
    }

}
