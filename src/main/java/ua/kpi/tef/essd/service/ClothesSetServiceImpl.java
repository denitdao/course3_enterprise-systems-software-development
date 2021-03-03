package ua.kpi.tef.essd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.kpi.tef.essd.dao.ClothesSetDao;
import ua.kpi.tef.essd.entity.ClothesSet;
import ua.kpi.tef.essd.entity.Clothing;
import ua.kpi.tef.essd.entity.User;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ClothesSetServiceImpl implements ClothesSetService {

    @Autowired
    private ClothesSetDao clothesSetDao;

    @Autowired
    private UserService userService;

    @Autowired
    private ClothingService clothingService;

    @Override
    @Transactional(readOnly = false)
    public void saveClothesSetOfUser(Integer userId, ClothesSet clothesSet) {
        userService.getUser(userId).addClothesSet(clothesSet);

        clothesSetDao.save(clothesSet);
    }

    @Override
    public List<ClothesSet> getClothesSetsOfUser(Integer userId) {
        return userService.getUser(userId).getClothesSets();
    }

    @Override
    @Transactional(readOnly = false)
    public ClothesSet getClothesSetOfClothing(Integer clothingId) {
        ClothesSet clothesSet = clothingService.getClothing(clothingId).getClothesSet();
        clothesSet.getName(); // lazy load all properties
        return clothesSet;
    }

    @Override
    public String getClothesSetInfo(Integer clothesSetId) {
        return clothesSetDao.findById(clothesSetId).toString();
    }

//  ---- Simple CRUD methods ----

    @Override
    @Transactional(readOnly = false)
    public void saveClothesSet(ClothesSet clothesSet) {
        clothesSetDao.save(clothesSet);
    }

    @Override
    public ClothesSet getClothesSet(Integer id) {
        return clothesSetDao.findById(id);
    }

    @Override
    @Transactional(readOnly = false)
    public ClothesSet updateClothesSet(ClothesSet clothesSet) {
        return clothesSetDao.update(clothesSet);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteClothesSet(Integer id) {
        clothesSetDao.deleteById(id);
    }
}
