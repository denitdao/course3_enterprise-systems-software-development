package ua.kpi.tef.essd.service;

import ua.kpi.tef.essd.entity.ClothesSet;
import ua.kpi.tef.essd.entity.Clothing;
import ua.kpi.tef.essd.entity.User;

import java.util.List;

public interface ClothesSetService {

    void saveClothesSet(ClothesSet clothesSet);

    void saveClothesSetOfUser(ClothesSet clothesSet, User user);

    ClothesSet getClothesSets(Integer id);

    List<ClothesSet> getClothesSetsOfUser(User user);

    ClothesSet getClothesSetOfClothing(Clothing clothing);

    String getClothesSetInfo(Integer clothesSetId);

    ClothesSet updateClothesSet(ClothesSet clothesSet);

    void deleteClothesSet(ClothesSet clothesSet);
}
