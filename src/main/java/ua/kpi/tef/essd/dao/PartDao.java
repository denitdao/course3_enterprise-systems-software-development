package ua.kpi.tef.essd.dao;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ua.kpi.tef.essd.entity.Part;

import java.util.List;

@Repository
public class PartDao extends GenericDao<Part> {

    public PartDao() {
        super(Part.class);
    }

    public List<Part> findByName(String name) {
        return entityManager
                .createQuery("select p from Part p where p.name like :name", Part.class)
                .setParameter("name", name + '%')
                .getResultList();
    }
}
