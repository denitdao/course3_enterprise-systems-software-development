package ua.kpi.tef.essd.ui.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.vaadin.artur.helpers.CrudService;
import org.vaadin.artur.helpers.CrudServiceDataProvider;
import ua.kpi.tef.essd.backend.entity.Role;
import ua.kpi.tef.essd.backend.entity.User;
import ua.kpi.tef.essd.backend.service.ClothingService;
import ua.kpi.tef.essd.backend.service.OrderService;
import ua.kpi.tef.essd.backend.service.RoleService;
import ua.kpi.tef.essd.backend.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Route(value = "user/all/:userID?/:action?(edit)", layout = MainView.class)
@PageTitle("Users")
public class UsersView extends Div implements BeforeEnterObserver {

    private final String USER_ID = "userID";
    private final String USER_EDIT_ROUTE_TEMPLATE = "user/all/%d/edit";

    private Grid<User> grid = new Grid<>(User.class, false);

    private TextField name;
    private TextField description;
    private TextField age;
//    private TextField role;

    private Button cancel = new Button("Cancel");
    private Button save = new Button("Save");

    private BeanValidationBinder<User> binder;

    private User user;

    private UserService userService;
    private ClothingService clothingService;
    private OrderService orderService;
    private RoleService roleService;

    public UsersView(UserService userService, ClothingService clothingService, OrderService orderService, RoleService roleService) {
        addClassNames("users-view", "flex", "flex-col", "h-full");
        this.userService = userService;
        this.clothingService = clothingService;
        this.orderService = orderService;
        // Create UI
        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        // Configure Grid
        grid.setItems(userService.getAllUsers());

        grid.addColumn(User::getId).setAutoWidth(true).setHeader("Id");
        grid.addColumn(User::getName).setAutoWidth(true).setHeader("Name");
        grid.addColumn(User::getDescription).setAutoWidth(true).setHeader("Description");
        grid.addColumn(User::getAge).setAutoWidth(true).setHeader("Age");
        grid.addColumn(u -> clothingService.getClothesOfUser(u.getId()).size()).setAutoWidth(true).setHeader("Clothes");
        grid.addColumn(u -> orderService.getOrdersOfUser(u.getId()).size()).setAutoWidth(true).setHeader("Orders");
        grid.addColumn(u -> roleService.getRolesOfUser(u.getId()).stream()
                .map(Role::getName)
                .collect(Collectors.joining(", "))
        ).setAutoWidth(true).setHeader("Roles");

        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeightFull();

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(String.format(USER_EDIT_ROUTE_TEMPLATE, event.getValue().getId()));
            } else {
                clearForm();
                UI.getCurrent().navigate(UsersView.class);
            }
        });

        // Configure Form
        binder = new BeanValidationBinder<>(User.class);

        // Bind fields. This where you'd define e.g. validation rules
        binder.forField(name).bind("name");
        binder.forField(description).bind("description");
        binder.forField(age).withConverter(new StringToIntegerConverter("Only numbers are allowed"))
                .bind("age");

        binder.bindInstanceFields(this);

        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(e -> {
            try {
                if (this.user == null) {
                    this.user = new User();
                }
                binder.writeBean(this.user);
                userService.saveUser(this.user);
                clearForm();
                refreshGrid();
                Notification.show("User details stored.");
                UI.getCurrent().navigate(UsersView.class);
            } catch (ValidationException validationException) {
                Notification.show("An exception happened while trying to store the user details.");
            }
        });

    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<Integer> userId = event.getRouteParameters().getInteger(USER_ID);
        if (userId.isPresent()) {
            Optional<User> userFromBackend = Optional.of(userService.getUser(userId.get()));
            if (userFromBackend.isPresent()) {
                populateForm(userFromBackend.get());
            } else {
                Notification.show(String.format("The requested user was not found, ID = %d", userId.get()), 3000,
                        Notification.Position.BOTTOM_START);
                // when a row is selected but the data is no longer available,
                // refresh grid
                refreshGrid();
                event.forwardTo(UsersView.class);
            }
        }
    }

    private void createEditorLayout(SplitLayout splitLayout) {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setClassName("flex flex-col");
        editorLayoutDiv.setWidth("400px");

        Div editorDiv = new Div();
        editorDiv.setClassName("p-l flex-grow");
        editorLayoutDiv.add(editorDiv);

        FormLayout formLayout = new FormLayout();
        name = new TextField("Name");
        description = new TextField("Description");
        age = new TextField("Age");
//        role = new TextField("Role");
        Component[] fields = new Component[]{name, description, age};

        for (Component field : fields) {
            ((HasStyle) field).addClassName("full-width");
        }
        formLayout.add(fields);
        editorDiv.add(formLayout);
        createButtonLayout(editorLayoutDiv);

        splitLayout.addToSecondary(editorLayoutDiv);
    }

    private void createButtonLayout(Div editorLayoutDiv) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setClassName("w-full flex-wrap bg-contrast-5 py-s px-l");
        buttonLayout.setSpacing(true);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save, cancel);
        editorLayoutDiv.add(buttonLayout);
    }

    private void createGridLayout(SplitLayout splitLayout) {
        Div wrapper = new Div();
        wrapper.setId("grid-wrapper");
        wrapper.setWidthFull();
        splitLayout.addToPrimary(wrapper);
        wrapper.add(grid);
    }

    private void refreshGrid() {
        grid.select(null);
//        grid.getDataProvider().refreshAll();
        grid.setItems(userService.getAllUsers());
    }

    private void clearForm() {
        populateForm(null);
    }

    private void populateForm(User value) {
        this.user = value;
        binder.readBean(this.user);
    }
}
