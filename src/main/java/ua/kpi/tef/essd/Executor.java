package ua.kpi.tef.essd;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ua.kpi.tef.essd.config.ApplicationConfiguration;
import ua.kpi.tef.essd.controller.ClothesSetController;
import ua.kpi.tef.essd.controller.ClothingController;
import ua.kpi.tef.essd.controller.UserController;
import ua.kpi.tef.essd.entity.*;

public class Executor {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);

        ClothingController clothingController = context.getBean(ClothingController.class);
        UserController userController = context.getBean(UserController.class);
        ClothesSetController clothesSetController = context.getBean(ClothesSetController.class);

        User user = new User("Tester", 10, "nothing interesting");
        Clothing clothing = new Clothing("Tester Shirt", Type.CHILDREN, Size.S);
        ClothesSet clothesSet = new ClothesSet("Tester's set", null);

        userController.createUser(user);

            System.out.println("\n" + userController.getUserInfo(user.getId()) + "\n");

        clothingController.createClothing(user.getId(), clothing);
        clothesSetController.createClothesSet(user.getId(), clothesSet);
        clothingController.addClothingToSet(clothesSet.getId(), clothing.getId());

            System.out.println("\n" + userController.getUserInfo(user.getId()) + "\n");
            System.out.println("\n" + clothingController.getClothingInfo(clothing.getId()) + "\n");
            System.out.println("\n" + clothesSetController.getClothesSetInfo(clothesSet.getId()) + "\n");
            //System.out.println(clothesSetController.getClothesSetWithClothing(clothing.getId()));

        context.close();
    }
}

// clothing service is able to call user service methods
// clothesSet service can call clothing service

// layer uses can be vertical or horizontal

// дизайнер - занимается созданием одежды (имеет свое описание)
// пользователь - может просмотреть каталог и выбрать модели одежды от дизайнеров для заказа.
// Заказ отправляется на
