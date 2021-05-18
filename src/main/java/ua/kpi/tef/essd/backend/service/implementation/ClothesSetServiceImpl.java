package ua.kpi.tef.essd.backend.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.kpi.tef.essd.backend.entity.ClothesSet;
import ua.kpi.tef.essd.backend.exception.ResourceNotFoundException;
import ua.kpi.tef.essd.backend.repository.ClothesSetRepository;
import ua.kpi.tef.essd.backend.service.ClothesSetService;
import ua.kpi.tef.essd.backend.service.ClothingService;
import ua.kpi.tef.essd.backend.service.UserService;
import ua.kpi.tef.essd.backend.util.EntityNames;

import java.util.List;

@Service
@Transactional
public class ClothesSetServiceImpl implements ClothesSetService {

    @Autowired
    private ClothesSetRepository clothesSetRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ClothingService clothingService;

    @Override
    public void saveClothesSetOfUser(Integer userId, ClothesSet clothesSet) {
        userService.getUser(userId).addClothesSet(clothesSet);
        clothesSetRepository.save(clothesSet);
    }

    @Override
    public List<ClothesSet> getClothesSetsOfUser(Integer userId) {
        return userService.getUser(userId).getClothesSets();
    }

    @Override
    public ClothesSet getClothesSetOfClothing(Integer clothingId) {
        return clothingService.getClothing(clothingId).getClothesSet(); // clothesSet.getName(); for lazy loading
    }

//  ---- Simple CRUD methods ----

    @Override
    public ClothesSet getClothesSet(Integer id) {
        return clothesSetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(EntityNames.CLOTHES_SET, id));
    }

    @Override
    public ClothesSet updateClothesSet(ClothesSet clothesSet) {
        return clothesSetRepository.save(clothesSet);
    }

    @Override
    public void deleteClothesSet(Integer id) {
        ClothesSet clothesSet = clothesSetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(EntityNames.CLOTHES_SET, id));
        clothesSet.getSetOfClothes()
                .forEach(clothesSet::removeClothing);
        clothesSetRepository.delete(clothesSet);
    }
}
