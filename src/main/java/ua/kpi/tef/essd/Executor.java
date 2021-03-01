package ua.kpi.tef.essd;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ua.kpi.tef.essd.config.ApplicationConfiguration;
import ua.kpi.tef.essd.controller.UserController;
import ua.kpi.tef.essd.dao.UserDao;
import ua.kpi.tef.essd.entity.*;
import ua.kpi.tef.essd.service.UserService;

public class Executor {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);

        UserDao userDao = context.getBean(UserDao.class);
        UserService userService = context.getBean(UserService.class);
        UserController userController = context.getBean(UserController.class);

        User user = new User("Tester", 10, "nothing interesting");
        Clothing clothing = new Clothing("Tester Shirt", Type.CHILDREN, Size.S);

        System.out.println(userController.getUserInfo(1));

        context.close();

        /*userService.createUser(user);
        ClothingController clothingController = new ClothingController();
        clothingController.createClothing(user.getId(), clothing);*/
    }
}
