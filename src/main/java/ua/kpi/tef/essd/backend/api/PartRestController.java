package ua.kpi.tef.essd.backend.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.kpi.tef.essd.backend.entity.ClothingPart;
import ua.kpi.tef.essd.backend.entity.Part;
import ua.kpi.tef.essd.backend.service.PartService;

import java.util.List;

/**
 * Use this controller to get or connect Parts to clothes.
 */
@RestController
@RequestMapping(value = "/api/part")
public class PartRestController {

    @Autowired
    private PartService partService;

    @PostMapping
    public void addPartToClothing(@RequestParam Integer clothingId, @RequestParam Integer partId, @RequestParam Integer amount) {
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
    public void updatePartInClothingAmount(@RequestParam Integer clothingId, @RequestParam Integer partId, @RequestParam Integer newAmount) {
        partService.changePartInClothingAmount(clothingId, partId, newAmount);
    }

}
