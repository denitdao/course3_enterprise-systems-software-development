package ua.kpi.tef.essd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ua.kpi.tef.essd.entity.ClothesSet;
import ua.kpi.tef.essd.entity.Clothing;
import ua.kpi.tef.essd.entity.User;
import ua.kpi.tef.essd.service.*;

import java.util.List;

/**
 * Use this controller to get or update Clothes.
 */
@Controller
public class ClothingController {

    @Autowired
    private ClothesSetService clothesSetService;

    @Autowired
    private ClothingService clothingService;

    @Autowired
    private UserService userService;

    public void createClothing(Integer userId, Clothing clothing) {
        User user = userService.getUser(userId);
        if(user != null) {
            clothingService.saveClothingOfUser(clothing, user);
        } else {
            System.out.println("no user with that id");
        }
    }

    public Clothing getClothingById(Integer clothingId) {
        return clothingService.getClothing(clothingId);
    }

    public List<Clothing> getClothesOfUser(Integer userId) {
        User user = userService.getUser(userId);
        if(user != null) {
            return clothingService.getClothesOfUser(user);
        } else {
            System.out.println("no user with that id");
            return null;
        }
    }

    public String getClothingInfo(Integer clothingId) {
        return clothingService.getClothingInfo(clothingId);
    }

    public void addClothingToSet(Integer setId, Integer clothingId) {
        ClothesSet clothesSet = clothesSetService.getClothesSets(setId);
        Clothing clothing = clothingService.getClothing(clothingId);
        if(clothesSet != null && clothing != null) {
            clothingService.addClothingToSet(clothing, clothesSet);
        } else {
            System.out.println("No clothing / set with id");
        }
    }

    public void updateClothing(Clothing clothing) {
        clothingService.updateClothing(clothing);
    }

    public void deleteClothing(Integer clothingId) {
        Clothing clothing = clothingService.getClothing(clothingId);
        if(clothing != null)
            clothingService.deleteClothing(clothing);
        else
            System.out.println("wrong id");
    }
}