package ua.kpi.tef.essd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ua.kpi.tef.essd.entity.ClothesSet;
import ua.kpi.tef.essd.entity.Clothing;
import ua.kpi.tef.essd.entity.User;
import ua.kpi.tef.essd.service.*;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Use this controller to get or update ClothingSets.
 */
@Controller
public class ClothesSetController {

    @Autowired
    private ClothesSetService clothesSetService;

    @Autowired
    private Validator validator;

    public void createClothesSet(Integer userId, ClothesSet clothesSet) {
        if (validator.validateUser(userId))
            clothesSetService.saveClothesSetOfUser(userId, clothesSet);
        else
            throw new NoSuchElementException("No user with specified id=" + userId + " found");
    }

    public ClothesSet getClothesSetById(Integer clothesSetId) {
        return clothesSetService.getClothesSet(clothesSetId);
    }

    public List<ClothesSet> getClothesSetsOfUser(Integer userId) {
        if (validator.validateUser(userId))
            return clothesSetService.getClothesSetsOfUser(userId);
        else
            throw new NoSuchElementException("No user with specified id=" + userId + " found");
    }

    public ClothesSet getClothesSetWithClothing(Integer clothingId) {
        if (validator.validateClothing(clothingId))
            return clothesSetService.getClothesSetOfClothing(clothingId);
        else
            throw new NoSuchElementException("No clothing with specified id=" + clothingId + " found");
    }

    public String getClothesSetInfo(Integer clothesSetId) {
        return clothesSetService.getClothesSetInfo(clothesSetId);
    }

    public void updateClothesSet(ClothesSet clothesSet) {
        clothesSetService.updateClothesSet(clothesSet);
    }

    public void deleteClothesSet(Integer clothesSetId) {
        if (validator.validateClothesSet(clothesSetId))
            clothesSetService.deleteClothesSet(clothesSetId);
        else
            throw new NoSuchElementException("No set with specified id=" + clothesSetId + " found");
    }

}