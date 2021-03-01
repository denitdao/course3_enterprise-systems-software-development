package ua.kpi.tef.essd.service;

import ua.kpi.tef.essd.entity.ClothesSet;
import ua.kpi.tef.essd.entity.Clothing;
import ua.kpi.tef.essd.entity.User;

import java.util.List;

public interface ClothingService {

    void saveClothing(Clothing clothing);

    void saveClothingOfUser(Clothing clothing, User user);

    Clothing getClothing(Integer id);

    List<Clothing> getClothesOfUser(User user);

    String getClothingInfo(Integer clothingId);

    void addClothingToSet(Clothing clothing, ClothesSet clothesSet);

    Clothing updateClothing(Clothing clothing);

    void deleteClothing(Clothing clothing);
}
