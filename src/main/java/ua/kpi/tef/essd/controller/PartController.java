package ua.kpi.tef.essd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.kpi.tef.essd.entity.ClothingPart;
import ua.kpi.tef.essd.entity.Part;
import ua.kpi.tef.essd.service.PartService;

import java.util.List;

/**
 * Use this controller to get or connect Parts to clothes.
 */
@RestController
@RequestMapping(value = "/part")
public class PartController {

    @Autowired
    private PartService partService;

    @PostMapping
    public void addPartToClothing(@RequestBody Integer clothingId, @RequestBody Integer partId, @RequestBody Integer amount) {
        partService.addPartToClothing(clothingId, partId, amount);
    }

    @GetMapping
    public List<Part> getAllParts() {
        return partService.getAllParts();
    }

    @GetMapping("/{id}")
    public Part getPartById(@PathVariable Integer id) {
        return partService.getPart(id);
    }

    @GetMapping("/clothing/{clothingId}")
    public List<ClothingPart> getClothingParts(@PathVariable Integer clothingId) {
        return partService.getClothingParts(clothingId);
    }

    @PutMapping
    public void updatePartInClothingAmount(@RequestBody Integer clothingId, @RequestBody Integer partId, @RequestBody Integer newAmount) {
        partService.changePartInClothingAmount(clothingId, partId, newAmount);
    }

}
