package ua.kpi.tef.essd.dao;

import org.springframework.stereotype.Repository;
import ua.kpi.tef.essd.entity.Property;

import java.util.List;

@Repository
public class PropertyDao extends GenericDao<Property> {

    public PropertyDao() {
        super(Property.class);
    }

    public List<Property> findByName(String name) {
        return entityManager
                .createQuery("select pr from Property pr where pr.name like :name", Property.class)
                .setParameter("name", name)
                .getResultList();
    }

    public List<Property> findByValue(String valueName) {
        return entityManager
                .createQuery("select pr from Property pr where pr.value like :valueName", Property.class)
                .setParameter("valueName", valueName)
                .getResultList();
    }

    public List<Property> findByNameAndValue(String name, String valueName) {
        return entityManager
                .createQuery("select pr from Property pr where pr.value like :valueName and pr.name like :name", Property.class)
                .setParameter("name", '%' + name + '%')
                .setParameter("valueName", '%' + valueName + '%')
                .getResultList();
    }
}
