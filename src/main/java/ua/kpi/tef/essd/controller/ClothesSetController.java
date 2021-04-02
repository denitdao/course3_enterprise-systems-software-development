package ua.kpi.tef.essd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.kpi.tef.essd.entity.ClothesSet;
import ua.kpi.tef.essd.service.*;
import ua.kpi.tef.essd.service.implementation.Validator;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Use this controller to get or update ClothingSets.
 */
@RestController
@RequestMapping(value = "/clothes_set")
public class ClothesSetController {

    @Autowired
    private ClothesSetService clothesSetService;

    @Autowired
    private Validator validator;

    @PostMapping
    public void createClothesSet(@RequestBody Integer userId, @RequestBody ClothesSet clothesSet) {
        if (validator.validateUser(userId))
            clothesSetService.saveClothesSetOfUser(userId, clothesSet);
        else
            throw new NoSuchElementException("No user with specified id=" + userId + " found");
    }

    @GetMapping("/{clothesSetId}")
    public ClothesSet getClothesSetById(@PathVariable Integer clothesSetId) {
        return clothesSetService.getClothesSet(clothesSetId);
    }

    @GetMapping("/user/{userId}")
    public List<ClothesSet> getClothesSetsOfUser(@PathVariable Integer userId) {
        if (validator.validateUser(userId))
            return clothesSetService.getClothesSetsOfUser(userId);
        else
            throw new NoSuchElementException("No user with specified id=" + userId + " found");
    }

    @GetMapping("/clothing/{clothingId}")
    public ClothesSet getClothesSetWithClothing(@PathVariable Integer clothingId) {
        if (validator.validateClothing(clothingId))
            return clothesSetService.getClothesSetOfClothing(clothingId);
        else
            throw new NoSuchElementException("No clothing with specified id=" + clothingId + " found");
    }

    @GetMapping("/info/{clothesSetId}")
    public String getClothesSetInfo(@PathVariable Integer clothesSetId) {
        return clothesSetService.getClothesSetInfo(clothesSetId);
    }

    @PutMapping
    public void updateClothesSet(@RequestBody ClothesSet clothesSet) {
        clothesSetService.updateClothesSet(clothesSet);
    }

    @DeleteMapping("/{clothesSetId}")
    public void deleteClothesSet(@PathVariable Integer clothesSetId) {
        if (validator.validateClothesSet(clothesSetId))
            clothesSetService.deleteClothesSet(clothesSetId);
        else
            throw new NoSuchElementException("No set with specified id=" + clothesSetId + " found");
    }

}