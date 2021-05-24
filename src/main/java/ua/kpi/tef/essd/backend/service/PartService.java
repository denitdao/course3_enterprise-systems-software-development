package ua.kpi.tef.essd.backend.service;

import ua.kpi.tef.essd.backend.entity.ClothingPart;
import ua.kpi.tef.essd.backend.entity.Part;

import java.util.List;

public interface PartService {

    void addPartToClothing(Integer clothingId, Integer partId, Integer amount);

    void changePartInClothingAmount(Integer clothingId, Integer partId, Integer amount);

    Part getPart(Integer id);

    List<Part> getAllParts();

    List<ClothingPart> getClothingParts(Integer clothingId);

}
