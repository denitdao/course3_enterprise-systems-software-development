package ua.kpi.tef.essd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.kpi.tef.essd.entity.Clothing;
import ua.kpi.tef.essd.service.*;

import java.util.List;

/**
 * Use this controller to get or update Clothes.
 */
@RestController
@RequestMapping(value = "/clothing")
public class ClothingController {

    @Autowired
    private ClothingService clothingService;

    @PostMapping
    public void createClothing(@RequestBody Integer userId, @RequestBody Clothing clothing) {
        clothingService.saveClothingOfUser(userId, clothing);
    }

    @GetMapping("/{clothingId}")
    public Clothing getClothingById(@PathVariable Integer clothingId) {
        return clothingService.getClothing(clothingId);
    }

    @GetMapping("/user/{userId}")
    public List<Clothing> getClothesOfUser(@PathVariable Integer userId) {
        return clothingService.getClothesOfUser(userId);
    }

    @PostMapping("/add_to_set")
    public void addClothingToSet(@RequestBody Integer setId, @RequestBody Integer clothingId) {
        clothingService.addClothingToSet(setId, clothingId);
    }

    @PutMapping
    public void updateClothing(@RequestBody Clothing clothing) {
        clothingService.updateClothing(clothing);
    }

    @DeleteMapping("/{clothingId}")
    public void deleteClothing(@PathVariable Integer clothingId) {
        clothingService.deleteClothing(clothingId);
    }
}