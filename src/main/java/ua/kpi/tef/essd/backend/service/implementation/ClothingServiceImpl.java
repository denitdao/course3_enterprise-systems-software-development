package ua.kpi.tef.essd.backend.service.implementation;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.kpi.tef.essd.backend.entity.Clothing;
import ua.kpi.tef.essd.backend.exception.ResourceNotFoundException;
import ua.kpi.tef.essd.backend.repository.ClothingRepository;
import ua.kpi.tef.essd.backend.service.ClothesSetService;
import ua.kpi.tef.essd.backend.service.ClothingService;
import ua.kpi.tef.essd.backend.service.UserService;
import ua.kpi.tef.essd.backend.util.EntityNames;

import java.util.List;

@Service
@Transactional
public class ClothingServiceImpl implements ClothingService {

    @Autowired
    private ClothingRepository clothingRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ClothesSetService clothesSetService;

    @Override
    public void saveClothingOfUser(Integer userId, Clothing clothing) {
        userService.getUser(userId).addClothing(clothing); // get connected to persistence user and link clothing
        clothingRepository.save(clothing); // insert clothing to the db
    }

    @Override
    public List<Clothing> getClothesOfUser(Integer userId) {
        List<Clothing> clothes = userService.getUser(userId).getClothes();
        Hibernate.initialize(clothes);
        return clothes;
    }

    @Override
    public void addClothingToSet(Integer clothesSetId, Integer clothingId) {
        Clothing clothing = getClothing(clothingId);
        clothesSetService.getClothesSet(clothesSetId).addClothing(clothing);
        clothingRepository.save(clothing);
    }

//  ---- Simple CRUD methods ----

    @Override
    public Clothing getClothing(Integer id) {
        return clothingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(EntityNames.CLOTHING, id));
    }

    @Override
    public Clothing updateClothing(Clothing clothing) {
        return clothingRepository.save(clothing);
    }

    @Override
    public void deleteClothing(Integer clothingId) {
        try {
            clothingRepository.deleteById(clothingId);
        } catch (DataAccessException ex) {
            throw new ResourceNotFoundException(EntityNames.CLOTHING, clothingId);
        }
    }

}
