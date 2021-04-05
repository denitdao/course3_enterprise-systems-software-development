package ua.kpi.tef.essd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.kpi.tef.essd.entity.Clothing;
import ua.kpi.tef.essd.service.*;
import ua.kpi.tef.essd.service.implementation.Validator;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Use this controller to get or update Clothes.
 */
@RestController
@RequestMapping(value = "/clothing")
public class ClothingController {

    @Autowired
    private ClothingService clothingService;

    @Autowired
    private Validator validator;

    @PostMapping
    public void createClothing(@RequestBody Integer userId, @RequestBody Clothing clothing) {
        if (validator.validateUser(userId))
            clothingService.saveClothingOfUser(userId, clothing);
        else
            throw new NoSuchElementException("No user with specified id=" + userId + " found");
    }

    @GetMapping("/{clothingId}")
    public Clothing getClothingById(@PathVariable Integer clothingId) {
        if (validator.validateClothing(clothingId))
            return clothingService.getClothing(clothingId);
        else
            throw new NoSuchElementException("No clothing with specified id=" + clothingId + " found");
    }

    @GetMapping("/user/{userId}")
    public List<Clothing> getClothesOfUser(@PathVariable Integer userId) {
        if (validator.validateUser(userId))
            return clothingService.getClothesOfUser(userId);
        else
            throw new NoSuchElementException("No user with specified id=" + userId + " found");
    }

    @GetMapping("/info/{clothingId}")
    public String getClothingInfo(@PathVariable Integer clothingId) {
        return clothingService.getClothingInfo(clothingId);
    }

    @PostMapping("/add_to_set")
    public void addClothingToSet(@RequestBody Integer setId, @RequestBody Integer clothingId) {
        if (!validator.validateClothesSet(setId))
            throw new NoSuchElementException("No set with specified id=" + setId + " found");
        if (!validator.validateClothing(clothingId))
            throw new NoSuchElementException("No clothing with specified id=" + clothingId + " found");
        clothingService.addClothingToSet(setId, clothingId);
    }

    @PutMapping
    public void updateClothing(@RequestBody Clothing clothing) {
        clothingService.updateClothing(clothing);
    }

    @DeleteMapping("/{clothingId}")
    public void deleteClothing(@PathVariable Integer clothingId) {
        if (validator.validateClothing(clothingId))
            clothingService.deleteClothing(clothingId);
        else
            throw new NoSuchElementException("No clothing with specified id=" + clothingId + " found");
    }
}