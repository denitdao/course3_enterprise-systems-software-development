package ua.kpi.tef.essd.entity;

import com.fasterxml.jackson.annotation.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "clothes")
@NoArgsConstructor
@Getter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Clothing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Setter
    private String name;

    @Enumerated(EnumType.ORDINAL)
    @Setter
    private Type type;

    @Enumerated(EnumType.ORDINAL)
    @Setter
    private Size size;

    @OneToMany(mappedBy = "clothing", cascade = CascadeType.MERGE)
    @Setter
    @JsonIgnoreProperties({"id", "clothing"})
    private List<ClothingPart> parts = new LinkedList<>();

    @OneToMany(mappedBy = "clothing", cascade = CascadeType.MERGE)
    @Setter
    @JsonIdentityReference(alwaysAsId=true)
    private List<Order> orders = new LinkedList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @Setter
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "clothes", "clothesSets", "orders", "roles"})
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clothes_set_id")
    @Setter
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "setOfClothes", "user"})
    private ClothesSet clothesSet;

    public Clothing(String name, Type type, Size size) {
        this.name = name;
        this.type = type;
        this.size = size;
    }

    public Clothing(String name, Type type, Size size, User user) {
        this.name = name;
        this.type = type;
        this.size = size;
        this.user = user;
    }

    public Clothing(String name, Type type, Size size, User user, ClothesSet clothesSet) {
        this.name = name;
        this.type = type;
        this.size = size;
        this.user = user;
        setClothesSet(clothesSet);
    }

    public void addPart(Part part, Integer amount) {
        ClothingPart clothingPart = new ClothingPart(this, part, amount);
        this.parts.add(clothingPart);
        part.getClothes().add(clothingPart);
    }

    public void removePart(Part part) {
        for (Iterator<ClothingPart> iterator = parts.iterator(); iterator.hasNext(); ) {
            ClothingPart clothingPart = iterator.next();
            if (clothingPart.getClothing().equals(this) && clothingPart.getPart().equals(part)) {
                iterator.remove(); // remove that part from clothing
                clothingPart.getPart().getClothes().remove(clothingPart); // remove clothing form that part
                clothingPart.setClothing(null);
                clothingPart.setPart(null);
                clothingPart.setAmount(null);
            }
        }
    }

    public void addOrder(User user, Integer amount, OrderStatus status) {
        Order order = new Order(user, this, amount, status);
        this.orders.add(order);
        user.getOrders().add(order);
    }

    public void removeOrder(Order order) {
        order.getClothing().getOrders().remove(order);
        order.getUser().getOrders().remove(order);
    }

    @Override
    public String toString() {
        return "Clothing{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", size=" + size +
                ", parts={" + parts.stream().map(c -> c.getPart().getName() + " (" + c.getAmount() + ")").collect(Collectors.joining(" | ")) +
                "}, user=" + user.getName() +
                ", orders={" + orders.stream().map(o -> o.getId().toString()).collect(Collectors.joining(" | ")) +
                "}, clothesSet=" + ((clothesSet != null) ? clothesSet.getName() : "none") +
                '}';
    }
}
