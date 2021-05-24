package ua.kpi.tef.essd.backend.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.kpi.tef.essd.backend.entity.ClothesSet;
import ua.kpi.tef.essd.backend.service.*;

import java.util.List;

/**
 * Use this controller to get or update ClothingSets.
 */
@RestController
@RequestMapping(value = "/api/clothes_set")
public class ClothesSetRestController {

    @Autowired
    private ClothesSetService clothesSetService;

    @PostMapping
    public void createClothesSet(@RequestParam Integer userId, @RequestBody ClothesSet clothesSet) {
        clothesSetService.saveClothesSetOfUser(userId, clothesSet);
    }

    @GetMapping("/{clothesSetId}")
    public ClothesSet getClothesSetById(@PathVariable Integer clothesSetId) {
        return clothesSetService.getClothesSet(clothesSetId);
    }

    @GetMapping("/user/{userId}")
    public List<ClothesSet> getClothesSetsOfUser(@PathVariable Integer userId) {
        return clothesSetService.getClothesSetsOfUser(userId);
    }

    @GetMapping("/clothing/{clothingId}")
    public ClothesSet getClothesSetWithClothing(@PathVariable Integer clothingId) {
        return clothesSetService.getClothesSetOfClothing(clothingId);
    }

    @PutMapping
    public void updateClothesSet(@RequestBody ClothesSet clothesSet) {
        clothesSetService.updateClothesSet(clothesSet);
    }

    @DeleteMapping("/{clothesSetId}")
    public void deleteClothesSet(@PathVariable Integer clothesSetId) {
        clothesSetService.deleteClothesSet(clothesSetId);
    }

}