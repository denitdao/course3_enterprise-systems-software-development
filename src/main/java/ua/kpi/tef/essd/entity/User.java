package ua.kpi.tef.essd.entity;

import com.fasterxml.jackson.annotation.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
//@Cacheable
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@NoArgsConstructor
@Getter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Setter
    private String name;

    @Setter
    private Integer age;

    @Setter
    private String description;

    @OneToMany(mappedBy = "user", cascade = CascadeType.MERGE, orphanRemoval = true)
    @JsonIgnoreProperties({"parts", "orders", "user", "clothesSet"})
    private final List<Clothing> clothes = new LinkedList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.MERGE, orphanRemoval = true)
    @JsonIgnoreProperties({"setOfClothes", "user"})
    private final List<ClothesSet> clothesSets = new LinkedList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.MERGE)
    @Setter
    @JsonIgnoreProperties({"user", "clothing"})
    private List<Order> orders = new LinkedList<>();

    @ManyToMany
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @JsonIgnoreProperties({"users"})
    private final List<Role> roles = new LinkedList<>();

    public User(String name, Integer age, String description) {
        this.name = name;
        this.age = age;
        this.description = description;
    }

    public User(String name, Integer age, String description, List<Clothing> clothes, List<ClothesSet> clothesSets, List<Role> roles) {
        this.name = name;
        this.age = age;
        this.description = description;
        setClothes(clothes);
        setClothesSets(clothesSets);
        setRoles(roles);
    }

    /**
     * Link both Clothing and User to each-other
     */
    public void addClothing(Clothing clothing) {
        this.clothes.add(clothing);
        clothing.setUser(this);
    }

    /**
     * Unlink both Clothing and User from each-other
     */
    public void removeClothing(Clothing clothing) {
        this.clothes.remove(clothing);
        clothing.setUser(null);
    }

    /**
     * Link both ClothesSet and User to each-other
     */
    public void addClothesSet(ClothesSet clothesSet) {
        this.clothesSets.add(clothesSet);
        clothesSet.setUser(this);
    }

    /**
     * Unlink both ClothesSet and User from each-other
     */
    public void removeClothesSet(ClothesSet clothesSet) {
        this.clothesSets.remove(clothesSet);
        clothesSet.setUser(null);
    }

    /**
     * Link both Role and User to each-other
     */
    public void addRole(Role role) {
        this.roles.add(role);
        role.getUsers().add(this);
    }

    /**
     * Unlink both Role and User from each-other
     */
    public void removeRole(Role role) {
        roles.remove(role);
        role.getUsers().remove(this);
    }

    public void addOrder(Clothing clothing, Integer amount, OrderStatus status) {
        Order order = new Order(this, clothing, amount, status);
        this.orders.add(order);
        clothing.getOrders().add(order);
    }

    public void removeOrder(Order order) {
        order.getClothing().getOrders().remove(order);
        order.getUser().getOrders().remove(order);
    }

    public void setClothes(List<Clothing> clothes) {
        if (clothes != null)
            clothes.forEach(this::addClothing);
    }

    public void setClothesSets(List<ClothesSet> clothesSets) {
        if (clothesSets != null)
            clothesSets.forEach(this::addClothesSet);
    }

    public void setRoles(List<Role> roles) {
        if (roles != null)
            roles.forEach(this::addRole);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", description='" + description + '\'' +
                ", clothes={" + clothes.stream().map(Clothing::getName).collect(Collectors.joining(" | ")) +
                "}, clothesSets={" + clothesSets.stream().map(ClothesSet::getName).collect(Collectors.joining(" | ")) +
                "}, roles={" + roles.stream().map(Role::getName).collect(Collectors.joining(" | ")) +
                "}}";
    }
}
