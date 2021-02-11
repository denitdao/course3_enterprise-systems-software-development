package ua.kpi.tef.essd.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "parts")
public class Part {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "part_property",
            joinColumns = @JoinColumn(name = "part_id"),
            inverseJoinColumns = @JoinColumn(name = "property_id"))
    private Set<Property> properties = new HashSet<>();

    @ManyToMany(mappedBy = "parts")
    private Set<Clothing> clothes = new HashSet<>();

    public void addProperty(Property property) {
        property.addPart(this);
        properties.add(property);
    }

    public void removeProperty(Property property) {
        properties.remove(property);
    }

    public void addClothing(Clothing clothing) {
        clothing.addPart(this);
        clothes.add(clothing);
    }

    public void removeClothing(Clothing clothing) {
        clothes.remove(clothing);
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

    public Set<Clothing> getClothes() {
        return clothes;
    }

    public void setClothes(Set<Clothing> clothes) {
        this.clothes = clothes;
    }

}
