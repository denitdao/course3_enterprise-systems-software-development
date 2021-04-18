package ua.kpi.tef.essd.service;

import ua.kpi.tef.essd.entity.ClothingPart;
import ua.kpi.tef.essd.entity.Part;

import java.util.List;

public interface PartService {

    void addPartToClothing(Integer clothingId, Integer partId, Integer amount);

    void changePartInClothingAmount(Integer clothingId, Integer partId, Integer amount);

    Part getPart(Integer id);

    List<Part> getAllParts();

    List<ClothingPart> getClothingParts(Integer clothingId);

}
