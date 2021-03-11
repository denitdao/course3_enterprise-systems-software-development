package ua.kpi.tef.essd.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
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
    private final List<Part> parts = new LinkedList<>();

    public Property() { }

    public Property(String name, String value, List<Part> parts) {
        this.name = name;
        this.value = value;
        setParts(parts);
    }

    public void addPart(Part part) {
        this.parts.add(part);
        part.getProperties().add(this);
    }

    public void removePart(Part part) {
        this.parts.remove(part);
        part.getProperties().remove(this);
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

    public List<Part> getParts() {
        return parts;
    }

    public void setParts(List<Part> parts) {
        if(parts != null)
            parts.forEach(this::addPart);
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
