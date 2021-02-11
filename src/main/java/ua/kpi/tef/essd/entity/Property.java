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

    @ManyToMany(mappedBy = "properties")
    private Set<Part> parts = new HashSet<>();

    public void addPart(Part part) {
        part.addProperty(this);
        parts.add(part);
    }

    public void removePart(Part part) {
        parts.remove(part);
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

}
