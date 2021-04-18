package ua.kpi.tef.essd.service;

import ua.kpi.tef.essd.entity.Clothing;

import java.util.List;

public interface ClothingService {

    void saveClothingOfUser(Integer userId, Clothing clothing);

    Clothing getClothing(Integer id);

    List<Clothing> getClothesOfUser(Integer userId);

    void addClothingToSet(Integer clothesSetId, Integer clothingId);

    Clothing updateClothing(Clothing clothing);

    void deleteClothing(Integer id);
}
