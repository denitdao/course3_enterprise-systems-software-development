package ua.kpi.tef.essd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ua.kpi.tef.essd.entity.ClothingPart;
import ua.kpi.tef.essd.entity.Part;
import ua.kpi.tef.essd.service.PartService;
import ua.kpi.tef.essd.service.implementation.Validator;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Use this controller to get or connect Parts to clothes.
 */
@Controller
public class PartController {

    @Autowired
    private PartService partService;

    @Autowired
    private Validator validator;

    public void addPartToClothing(Integer clothingId, Integer partId, Integer amount) {
        if (!validator.validateClothing(clothingId))
            throw new NoSuchElementException("No clothing with specified id=" + clothingId + " found");
        if (!validator.validatePart(partId))
            throw new NoSuchElementException("No part with specified id=" + partId + " found");
        partService.addPartToClothing(clothingId, partId, amount);
    }

    public List<Part> getAllParts() {
        return partService.getAllParts();
    }

    public Part getPartById(Integer id) {
        if (validator.validatePart(id))
            return partService.getPart(id);
        else
            throw new NoSuchElementException("No part with specified id=" + id + " found");
    }

    public List<ClothingPart> getClothingParts(Integer clothingId) {
        if (validator.validateClothing(clothingId))
            return partService.getClothingParts(clothingId);
        else
            throw new NoSuchElementException("No clothing with specified id=" + clothingId + " found");
    }

    public String getPartInfo(Integer partId) {
        return partService.getPartInfo(partId);
    }

    public void updatePartInClothingAmount(Integer clothingId, Integer partId, Integer newAmount) {
        if (!validator.validateClothing(clothingId))
            throw new NoSuchElementException("No clothing with specified id=" + clothingId + " found");
        if (!validator.validatePart(partId))
            throw new NoSuchElementException("No part with specified id=" + partId + " found");
        partService.changePartInClothingAmount(clothingId, partId, newAmount);
    }

}
