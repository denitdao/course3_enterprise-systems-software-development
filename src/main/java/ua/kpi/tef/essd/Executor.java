package ua.kpi.tef.essd;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ua.kpi.tef.essd.config.ApplicationConfiguration;
import ua.kpi.tef.essd.controller.ClothesSetController;
import ua.kpi.tef.essd.controller.ClothingController;
import ua.kpi.tef.essd.controller.PartController;
import ua.kpi.tef.essd.controller.UserController;
import ua.kpi.tef.essd.entity.*;

@Log4j2
@SpringBootApplication
public class Executor {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);

        ClothingController clothingController = context.getBean(ClothingController.class);
        UserController userController = context.getBean(UserController.class);
        ClothesSetController clothesSetController = context.getBean(ClothesSetController.class);
        PartController partController = context.getBean(PartController.class);

        User user = new User("Tester", 10, "nothing interesting");
        Clothing clothing = new Clothing("Tester Shirt", Type.CHILDREN, Size.S);
        ClothesSet clothesSet = new ClothesSet("Tester's set", null);

        userController.createUser(user);

            log.info(userController.getUserInfo(user.getId()));

        clothingController.createClothing(user.getId(), clothing);
        clothesSetController.createClothesSet(user.getId(), clothesSet);
        clothingController.addClothingToSet(clothesSet.getId(), clothing.getId());

        partController.addPartToClothing(clothing.getId(), 2, 10);

        log.info(clothingController.getClothingInfo(clothing.getId()));
        log.info(partController.getPartInfo(2));

        partController.updatePartInClothingAmount(clothing.getId(), 2, 5);
        partController.updatePartInClothingAmount(clothing.getId(), 4, 6);

        log.info(clothingController.getClothingInfo(clothing.getId()));

        context.close();
    }
}

// дизайнер - занимается созданием одежды (имеет свое описание)
// пользователь - может просмотреть каталог и выбрать модели одежды от дизайнеров для заказа.
// заказы - сохраняются в бд и отправляются поставщику
// поставщик - просматривает заказы, меняет статусы | может добавлять детали для одежды
