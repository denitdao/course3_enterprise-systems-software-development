package ua.kpi.tef.essd.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "clothes")
public class Clothing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Enumerated(EnumType.ORDINAL)
    private Type type;

    @Enumerated(EnumType.ORDINAL)
    private Size size;

    @OneToMany(mappedBy = "clothing", cascade = CascadeType.ALL)
    private Set<ClothingPart> parts = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clothes_set_id")
    private ClothesSet clothesSet;

    public Clothing() { }

    public Clothing(String name, Type type, Size size, User user) {
        this.name = name;
        this.type = type;
        this.size = size;
        this.user = user;
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

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public Set<ClothingPart> getParts() {
        return parts;
    }

    public void setParts(Set<ClothingPart> parts) {
        this.parts = parts;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ClothesSet getClothesSet() {
        return clothesSet;
    }

    public void setClothesSet(ClothesSet clothesSet) {
        this.clothesSet = clothesSet;
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
                ", clothesSet=" + ((clothesSet != null) ? clothesSet.getName() : "none") +
                '}';
    }
}
