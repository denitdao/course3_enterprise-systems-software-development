package ua.kpi.tef.essd.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Entity that represents Parts values.
 * [Color, Material, Length, Style, Fit]
 */
@Entity
@Table(name = "properties")
@NoArgsConstructor
@Getter
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Setter
    private String name;

    @Setter
    private String value;

    @ManyToMany(mappedBy = "properties", cascade = CascadeType.ALL)
    private final List<Part> parts = new LinkedList<>();

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

    public void setParts(List<Part> parts) {
        if (parts != null)
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
