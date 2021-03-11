package ua.kpi.tef.essd.service;

import ua.kpi.tef.essd.entity.ClothesSet;

import java.util.List;

public interface ClothesSetService {

    void saveClothesSet(ClothesSet clothesSet);

    void saveClothesSetOfUser(Integer userId, ClothesSet clothesSet);

    ClothesSet getClothesSet(Integer id);

    List<ClothesSet> getClothesSetsOfUser(Integer userId);

    ClothesSet getClothesSetOfClothing(Integer clothingId);

    String getClothesSetInfo(Integer id);

    ClothesSet updateClothesSet(ClothesSet clothesSet);

    void deleteClothesSet(Integer id);
}
