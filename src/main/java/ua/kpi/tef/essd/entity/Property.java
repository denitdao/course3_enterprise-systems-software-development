package ua.kpi.tef.essd.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity that represents Parts values.
 * [Color, Material, Length, Style, Fit]
 */
@Entity
@Table(name = "properties")
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String value;

    @ManyToMany(mappedBy = "properties", cascade = CascadeType.ALL)
    private Set<Part> parts = new HashSet<>();

    public Property() { }

    public Property(String name, String value, Set<Part> parts) {
        this.name = name;
        this.value = value;
        if(parts != null)
            parts.forEach(this::addPart);
    }

    public void addPart(Part part) {
        part.getProperties().add(this);
        this.parts.add(part);
    }

    public void removePart(Part part) {
        part.getProperties().remove(this);
        this.parts.remove(part);
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Set<Part> getParts() {
        return parts;
    }

    public void setParts(Set<Part> parts) {
        this.parts = parts;
    }

    @Override
    public String toString() {
        return "Property{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
