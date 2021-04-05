package ua.kpi.tef.essd.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "parts")
@NoArgsConstructor
@Getter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Part {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Setter
    private String name;

    @ManyToMany(cascade = CascadeType.ALL) // inserts properties itself
    @JoinTable(
            name = "part_property",
            joinColumns = @JoinColumn(name = "part_id"),
            inverseJoinColumns = @JoinColumn(name = "property_id"))
    @Setter
    private List<Property> properties = new LinkedList<>();

    @OneToMany(mappedBy = "part", cascade = CascadeType.MERGE)
    @Setter
    @JsonIgnore
    private List<ClothingPart> clothes = new LinkedList<>();

    public Part(String name, List<Property> properties) {
        this.name = name;
        if (properties != null)
            properties.forEach(this::addProperty);
    }

    public void addProperty(Property property) {
        this.properties.add(property);
        property.getParts().add(this);
    }

    public void removeProperty(Property property) {
        properties.remove(property);
        property.getParts().remove(this);
    }

    public void addClothing(Clothing clothing, Integer amount) {
        ClothingPart clothingPart = new ClothingPart(clothing, this, amount);
        this.clothes.add(clothingPart);
        clothing.getParts().add(clothingPart);
    }

    public void removeClothing(Clothing clothing) {
        for (Iterator<ClothingPart> iterator = clothes.iterator(); iterator.hasNext(); ) {
            ClothingPart clothingPart = iterator.next();
            if (clothingPart.getClothing().equals(clothing) && clothingPart.getPart().equals(this)) {
                iterator.remove(); // remove that clothing from part
                clothingPart.getClothing().getParts().remove(clothingPart); // remove part form that clothing
                clothingPart.setClothing(null);
                clothingPart.setPart(null);
                clothingPart.setAmount(null);
            }
        }
    }

    @Override
    public String toString() {
        return "Part{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", properties=" + properties +
                ", clothes={" + clothes.stream().map(c -> c.getClothing().getName()).collect(Collectors.joining(" | ")) +
                "}}";
    }
}
