package ua.kpi.tef.essd.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "clothes_set")
public class ClothesSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @OneToMany(mappedBy = "clothesSet", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Clothes> clothesSet;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
