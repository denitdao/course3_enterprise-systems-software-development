package ua.kpi.tef.essd.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "parts")
public class Part {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    // inserts properties itself
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "part_property",
            joinColumns = @JoinColumn(name = "part_id"),
            inverseJoinColumns = @JoinColumn(name = "property_id"))
    private Set<Property> properties = new HashSet<>();

    // have to manually add clothes to the table
    @OneToMany(mappedBy = "part", cascade = CascadeType.MERGE)
    private Set<ClothingPart> clothes = new HashSet<>();

    public Part() { }

    public Part(String name, Set<Property> properties) {
        this.name = name;
        if(properties != null)
            properties.forEach(this::addProperty);
    }

    public void addProperty(Property property) {
        property.getParts().add(this);
        this.properties.add(property);
    }

    public void removeProperty(Property property) {
        property.getParts().remove(this);
        properties.remove(property);
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

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Property> getProperties() {
        return properties;
    }

    public void setProperties(Set<Property> properties) {
        this.properties = properties;
    }

    public Set<ClothingPart> getClothes() {
        return clothes;
    }

    public void setClothes(Set<ClothingPart> clothes) {
        this.clothes = clothes;
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
