package ua.kpi.tef.essd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.kpi.tef.essd.dao.ClothesSetDao;
import ua.kpi.tef.essd.dao.ClothingDao;
import ua.kpi.tef.essd.dao.UserDao;
import ua.kpi.tef.essd.entity.ClothesSet;
import ua.kpi.tef.essd.entity.Clothing;
import ua.kpi.tef.essd.entity.User;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ClothingServiceImpl implements ClothingService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private ClothingDao clothingDao;

    @Autowired
    private ClothesSetDao clothesSetDao;

    @Override
    @Transactional(readOnly = false)
    public void saveClothing(Clothing clothing) {
        clothingDao.save(clothing);
    }

    @Override
    @Transactional(readOnly = false)
    public void saveClothingOfUser(Clothing clothing, User user) {
        userDao.update(user).addClothing(clothing); // connect user back to persistence and link clothing
        clothingDao.save(clothing); // insert clothing to the db
    }

    @Override
    public Clothing getClothing(Integer id) {
        return clothingDao.findById(id);
    }

    @Override
    public List<Clothing> getClothesOfUser(User user) {
        return user.getClothes();
    }

    @Override
    public String getClothingInfo(Integer clothingId) {
        return clothingDao.findById(clothingId).toString();
    }

    @Override
    @Transactional(readOnly = false)
    public void addClothingToSet(Clothing clothing, ClothesSet clothesSet) {
        clothing = clothingDao.update(clothing);
        clothesSetDao.update(clothesSet).addClothing(clothing);
    }

    @Override
    @Transactional(readOnly = false)
    public Clothing updateClothing(Clothing clothing) {
        return clothingDao.update(clothing);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteClothing(Clothing clothing) {
        clothingDao.delete(clothing);
    }

    /*@Override
    @Transactional(readOnly = false)
    public void createClothingForUser(Clothing clothing, User user) {
        clothingDao.save(clothing); // save our clothing
        userDao.update(user).addClothing(clothing); // update user
        clothingDao.update(clothing);
    }*/

}
