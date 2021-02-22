package ua.kpi.tef.essd;

import ua.kpi.tef.essd.dao.ClothingDao;
import ua.kpi.tef.essd.dao.PropertyDao;
import ua.kpi.tef.essd.dao.UserDao;
import ua.kpi.tef.essd.entity.*;

import java.util.Set;

public class Executor {

    public static void main(String[] args) {
        ClothingDao clothingDao = new ClothingDao();
        UserDao userDao = new UserDao();
        PropertyDao propertyDao = new PropertyDao();

        Property property1 = new Property("Color", "Blue", null);
        Property property2 = new Property("Material", "Cotton", null);

        Part part = new Part("Part 1", Set.of(property1, property2));

        Clothing clothing = new Clothing("Clothing 1", Type.MAN, Size.S, null, null);
        clothing.addPart(part, 3);
        clothingDao.save(clothing);

        ClothesSet clothesSet = new ClothesSet("Set 1", Set.of(clothing), null);

        User user = new User("User name", 19, "about me", Set.of(clothing), Set.of(clothesSet));

        userDao.update(user);

        System.out.println(userDao.findById(1));
        System.out.println(clothingDao.findBySize(Size.S));
        System.out.println(propertyDao.findByValue("Blue"));
        System.out.println(propertyDao.findByNameAndValue("", ""));
    }
}
