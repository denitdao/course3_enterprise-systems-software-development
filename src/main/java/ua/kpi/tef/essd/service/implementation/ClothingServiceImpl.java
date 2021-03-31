package ua.kpi.tef.essd.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.kpi.tef.essd.entity.Clothing;
import ua.kpi.tef.essd.exception.ResourceNotFoundException;
import ua.kpi.tef.essd.repository.ClothingRepository;
import ua.kpi.tef.essd.service.ClothesSetService;
import ua.kpi.tef.essd.service.ClothingService;
import ua.kpi.tef.essd.service.UserService;
import ua.kpi.tef.essd.util.EntityNames;

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
        return userService.getUser(userId).getClothes();
    }

    @Override
    public String getClothingInfo(Integer clothingId) {
        return clothingRepository.findById(clothingId)
                .orElseThrow(() -> new ResourceNotFoundException(EntityNames.CLOTHING, clothingId)).toString();
    }

    @Override
    public void addClothingToSet(Integer clothesSetId, Integer clothingId) {
        Clothing clothing = clothingRepository.findById(clothingId)
                .orElseThrow(() -> new ResourceNotFoundException(EntityNames.CLOTHING, clothingId));
        clothesSetService.getClothesSet(clothesSetId).addClothing(clothing);
    }

//  ---- Simple CRUD methods ----

    @Override
    public void saveClothing(Clothing clothing) {
        clothingRepository.save(clothing);
    }

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
        clothingRepository.deleteById(clothingId);
    }

}
