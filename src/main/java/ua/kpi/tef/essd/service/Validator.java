package ua.kpi.tef.essd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.kpi.tef.essd.dao.*;

@Service
public class Validator {

    @Autowired
    private UserDao userDao;

    @Autowired
    private ClothingDao clothingDao;

    @Autowired
    private ClothesSetDao clothesSetDao;

    @Autowired
    private PartDao partDao;

    @Autowired
    private OrderDao orderDao;

    public boolean validateUser(Integer id) {
        return userDao.validate(id);
    }

    public boolean validateClothing(Integer id) {
        return clothingDao.validate(id);
    }

    public boolean validateClothesSet(Integer id) {
        return clothesSetDao.validate(id);
    }

    public boolean validatePart(Integer id) { return partDao.validate(id); }

    public boolean validateOrder(Integer id) { return orderDao.validate(id); }
}
