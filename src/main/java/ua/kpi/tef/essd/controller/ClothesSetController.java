package ua.kpi.tef.essd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ua.kpi.tef.essd.entity.ClothesSet;
import ua.kpi.tef.essd.entity.Clothing;
import ua.kpi.tef.essd.entity.User;
import ua.kpi.tef.essd.service.*;

import java.util.List;

/**
 * Use this controller to get or update ClothingSets.
 */
@Controller
public class ClothesSetController {

    @Autowired
    private ClothesSetService clothesSetService;

    @Autowired
    private ClothingService clothingService;

    @Autowired
    private UserService userService;

    public void createClothesSet(Integer userId, ClothesSet clothesSet) {
        User user = userService.getUser(userId);
        if(user != null) {
            clothesSetService.saveClothesSetOfUser(clothesSet, user);
        } else {
            System.out.println("no user with that id");
        }
    }

    public ClothesSet getClothesSetById(Integer clothesSetId) {
        return clothesSetService.getClothesSets(clothesSetId);
    }

    public List<ClothesSet> getClothesSetsOfUser(Integer userId) {
        User user = userService.getUser(userId);
        if(user != null) {
            return clothesSetService.getClothesSetsOfUser(user);
        } else {
            System.out.println("no user with that id");
            return null;
        }
    }

    public ClothesSet getClothesSetWithClothing(Integer clothingId) {
        Clothing clothing = clothingService.getClothing(clothingId);
        if(clothing != null) {
            return clothesSetService.getClothesSetOfClothing(clothing);
        } else {
            System.out.println("no clothing with that id");
            return null;
        }
    }

    public String getClothesSetInfo(Integer clothesSetId) {
        return clothesSetService.getClothesSetInfo(clothesSetId);
    }

    public void updateClothesSet(ClothesSet clothesSet) {
        clothesSetService.updateClothesSet(clothesSet);
    }

    public void deleteClothesSet(Integer clothesSetId) {
        ClothesSet clothesSet = clothesSetService.getClothesSets(clothesSetId);
        if(clothesSet != null)
            clothesSetService.deleteClothesSet(clothesSet);
        else
            System.out.println("wrong id");
    }

}