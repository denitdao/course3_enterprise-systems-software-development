package ua.kpi.tef.essd.backend.service;

import ua.kpi.tef.essd.backend.entity.ClothesSet;

import java.util.List;

public interface ClothesSetService {

    void saveClothesSetOfUser(Integer userId, ClothesSet clothesSet);

    ClothesSet getClothesSet(Integer id);

    List<ClothesSet> getClothesSetsOfUser(Integer userId);

    ClothesSet getClothesSetOfClothing(Integer clothingId);

    ClothesSet updateClothesSet(ClothesSet clothesSet);

    void deleteClothesSet(Integer id);
}
