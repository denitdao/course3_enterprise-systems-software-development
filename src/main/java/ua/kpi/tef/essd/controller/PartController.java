package ua.kpi.tef.essd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.kpi.tef.essd.entity.ClothingPart;
import ua.kpi.tef.essd.entity.Part;
import ua.kpi.tef.essd.service.PartService;
import ua.kpi.tef.essd.service.implementation.Validator;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Use this controller to get or connect Parts to clothes.
 */
@RestController
@RequestMapping(value = "/part")
public class PartController {

    @Autowired
    private PartService partService;

    @Autowired
    private Validator validator;

    @PostMapping
    public void addPartToClothing(@RequestBody Integer clothingId, @RequestBody Integer partId, @RequestBody Integer amount) {
        if (!validator.validateClothing(clothingId))
            throw new NoSuchElementException("No clothing with specified id=" + clothingId + " found");
        if (!validator.validatePart(partId))
            throw new NoSuchElementException("No part with specified id=" + partId + " found");
        partService.addPartToClothing(clothingId, partId, amount);
    }

    @GetMapping
    public List<Part> getAllParts() {
        return partService.getAllParts();
    }

    @GetMapping("/{id}")
    public Part getPartById(@PathVariable Integer id) {
        if (validator.validatePart(id))
            return partService.getPart(id);
        else
            throw new NoSuchElementException("No part with specified id=" + id + " found");
    }

    @GetMapping("/clothing/{clothingId}")
    public List<ClothingPart> getClothingParts(@PathVariable Integer clothingId) {
        if (validator.validateClothing(clothingId))
            return partService.getClothingParts(clothingId);
        else
            throw new NoSuchElementException("No clothing with specified id=" + clothingId + " found");
    }

    @GetMapping("/info/{partId}")
    public String getPartInfo(@PathVariable Integer partId) {
        return partService.getPartInfo(partId);
    }

    @PutMapping
    public void updatePartInClothingAmount(@RequestBody Integer clothingId, @RequestBody Integer partId, @RequestBody Integer newAmount) {
        if (!validator.validateClothing(clothingId))
            throw new NoSuchElementException("No clothing with specified id=" + clothingId + " found");
        if (!validator.validatePart(partId))
            throw new NoSuchElementException("No part with specified id=" + partId + " found");
        partService.changePartInClothingAmount(clothingId, partId, newAmount);
    }

}
