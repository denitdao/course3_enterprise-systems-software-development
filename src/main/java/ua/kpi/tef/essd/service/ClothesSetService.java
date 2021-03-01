package ua.kpi.tef.essd.service;

import ua.kpi.tef.essd.entity.ClothesSet;
import ua.kpi.tef.essd.entity.User;

import java.util.List;

public interface ClothesSetService {

    void saveClothesSet(ClothesSet clothesSet);

    void saveClothesSetOfUser(ClothesSet clothesSet, User user);

    ClothesSet getClothesSets(Integer id);

    List<ClothesSet> getClothesSetsOfUser(User user);

    String getClothesSetInfo(ClothesSet clothesSet);

    ClothesSet updateClothesSet(ClothesSet clothesSet);

    void deleteClothesSet(ClothesSet clothesSet);
}
