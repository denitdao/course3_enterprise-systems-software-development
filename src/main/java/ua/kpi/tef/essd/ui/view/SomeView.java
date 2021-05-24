package ua.kpi.tef.essd.ui.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import ua.kpi.tef.essd.backend.api.ClothingRestController;
import ua.kpi.tef.essd.backend.api.UserRestController;
import ua.kpi.tef.essd.backend.entity.User;

import javax.annotation.PostConstruct;

@Route("users")
public class SomeView extends VerticalLayout {

    @Autowired
    private UserRestController userRestController;

    @Autowired
    private ClothingRestController clothingRestController;

    public SomeView() {
    }

    @PostConstruct
    public void init() {
        TextField textField = new TextField("Type user number");
        Button button = new Button("Show name", e -> Notification.show("hello"));

        Grid<User> grid = new Grid<>(User.class);

        grid.setItems(userRestController.getAllUsers());
        grid.setColumns("id", "name", "age", "description");
        try {
            grid.addColumn(p -> clothingRestController.getClothesOfUser(p.getId()).size()).setHeader("Clothing amount");
        } catch (Exception e) {
            Notification.show(e.getMessage());
        }
        add(
                new HorizontalLayout(
                        textField,
                        button
                ), grid
        );
    }
}
