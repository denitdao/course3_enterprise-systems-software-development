package ua.kpi.tef.essd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.kpi.tef.essd.dao.ClothingDao;
import ua.kpi.tef.essd.entity.Clothing;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ClothingServiceImpl implements ClothingService {

    @Autowired
    private ClothingDao clothingDao;

    @Autowired
    private UserService userService;

    @Autowired
    private ClothesSetService clothesSetService;

    @Override
    @Transactional(readOnly = false)
    public void saveClothingOfUser(Integer userId, Clothing clothing) {
        userService.getUser(userId).addClothing(clothing); // get connected to persistence user and link clothing
        clothingDao.save(clothing); // insert clothing to the db
    }

    @Override
    public List<Clothing> getClothesOfUser(Integer userId) {
        return userService.getUser(userId).getClothes();
    }

    @Override
    public String getClothingInfo(Integer clothingId) {
        return clothingDao.findById(clothingId).toString();
    }

    @Override
    @Transactional(readOnly = false)
    public void addClothingToSet(Integer clothesSetId, Integer clothingId) {
        Clothing clothing = clothingDao.findById(clothingId);
        clothesSetService.getClothesSet(clothesSetId).addClothing(clothing);
    }

//  ---- Simple CRUD methods ----

    @Override
    @Transactional(readOnly = false)
    public void saveClothing(Clothing clothing) {
        clothingDao.save(clothing);
    }

    @Override
    public Clothing getClothing(Integer id) {
        return clothingDao.findById(id);
    }

    @Override
    @Transactional(readOnly = false)
    public Clothing updateClothing(Clothing clothing) {
        return clothingDao.update(clothing);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteClothing(Integer clothingId) {
        clothingDao.deleteById(clothingId);
    }

}
