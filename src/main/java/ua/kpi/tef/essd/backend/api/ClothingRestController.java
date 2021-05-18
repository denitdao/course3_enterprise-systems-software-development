package ua.kpi.tef.essd.backend.api;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.kpi.tef.essd.backend.entity.Clothing;
import ua.kpi.tef.essd.backend.service.*;

import java.util.List;

/**
 * Use this controller to get or update Clothes.
 */
@RestController
@RequestMapping(value = "/api/clothing")
public class ClothingRestController {

    @Autowired
    private ClothingService clothingService;

    @PostMapping
    public void createClothing(@RequestParam Integer userId, @RequestBody Clothing clothing) {
        clothingService.saveClothingOfUser(userId, clothing);
    }

    @GetMapping("/{clothingId}")
    public Clothing getClothingById(@PathVariable Integer clothingId) {
        return clothingService.getClothing(clothingId);
    }

    @GetMapping("/user/{userId}")
    public List<Clothing> getClothesOfUser(@PathVariable Integer userId) {
        List<Clothing> clothesOfUser = clothingService.getClothesOfUser(userId);
        System.out.println(clothesOfUser.size());
        return clothesOfUser;
    }

    @PostMapping("/add_to_set")
    public void addClothingToSet(@RequestParam Integer setId, @RequestParam Integer clothingId) {
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