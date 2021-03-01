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
public class ClothesSetServiceImpl implements ClothesSetService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private ClothingDao clothingDao;

    @Autowired
    private ClothesSetDao clothesSetDao;

    @Override
    @Transactional(readOnly = false)
    public void saveClothesSet(ClothesSet clothesSet) {
        clothesSetDao.save(clothesSet);
    }

    @Override
    @Transactional(readOnly = false)
    public void saveClothesSetOfUser(ClothesSet clothesSet, User user) {
        userDao.update(user).addClothesSet(clothesSet);
        clothesSetDao.save(clothesSet);
    }

    @Override
    public ClothesSet getClothesSets(Integer id) {
        return clothesSetDao.findById(id);
    }

    @Override
    public List<ClothesSet> getClothesSetsOfUser(User user) {
        return userDao.update(user).getClothesSets();
    }

    @Override
    @Transactional(readOnly = false)
    public ClothesSet getClothesSetOfClothing(Clothing clothing) {
        ClothesSet clothesSet = clothingDao.update(clothing).getClothesSet();
        clothesSet.getName();
        return clothesSet; // todo
    }

    @Override
    public String getClothesSetInfo(Integer clothesSetId) {
        return clothesSetDao.findById(clothesSetId).toString();
    }

    @Override
    @Transactional(readOnly = false)
    public ClothesSet updateClothesSet(ClothesSet clothesSet) {
        return clothesSetDao.update(clothesSet);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteClothesSet(ClothesSet clothesSet) {
        clothesSetDao.delete(clothesSet);
    }
}
