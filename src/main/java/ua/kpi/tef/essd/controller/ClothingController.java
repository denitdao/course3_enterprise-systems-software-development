package ua.kpi.tef.essd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ua.kpi.tef.essd.entity.Clothing;
import ua.kpi.tef.essd.service.*;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Use this controller to get or update Clothes.
 */
@Controller
public class ClothingController {

    @Autowired
    private ClothingService clothingService;

    @Autowired
    private Validator validator;

    public void createClothing(Integer userId, Clothing clothing) throws RuntimeException {
        if (validator.validateUser(userId)) {
            clothingService.saveClothingOfUser(userId, clothing);
        } else
            throw new NoSuchElementException("No user with specified id=" + userId + " found");
    }

    public Clothing getClothingById(Integer clothingId) throws RuntimeException {
        if (validator.validateClothing(clothingId)) {
            return clothingService.getClothing(clothingId);
        } else
            throw new NoSuchElementException("No clothing with specified id=" + clothingId + " found");
    }

    public List<Clothing> getClothesOfUser(Integer userId) {
        if (validator.validateUser(userId)) {
            return clothingService.getClothesOfUser(userId);
        } else
            throw new NoSuchElementException("No user with specified id=" + userId + " found");
    }

    public String getClothingInfo(Integer clothingId) {
        return clothingService.getClothingInfo(clothingId);
    }

    public void addClothingToSet(Integer setId, Integer clothingId) {
        if (!validator.validateClothesSet(setId))
            throw new NoSuchElementException("No set with specified id=" + setId + " found");
        if (!validator.validateClothing(clothingId))
            throw new NoSuchElementException("No clothing with specified id=" + clothingId + " found");
        clothingService.addClothingToSet(setId, clothingId);
    }

    public void updateClothing(Clothing clothing) {
        clothingService.updateClothing(clothing);
    }

    public void deleteClothing(Integer clothingId) {
        if (validator.validateClothing(clothingId)) {
            clothingService.deleteClothing(clothingId);
        } else
            throw new NoSuchElementException("No user with specified id=" + clothingId + " found");
    }
}