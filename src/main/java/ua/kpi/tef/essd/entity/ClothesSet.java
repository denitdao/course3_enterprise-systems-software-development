package ua.kpi.tef.essd.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "clothes_sets")
@NoArgsConstructor
@Getter
public class ClothesSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Setter
    private String name;

    @OneToMany(mappedBy = "clothesSet", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Clothing> setOfClothes = new LinkedList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @Setter
    private User user;

    public ClothesSet(String name, List<Clothing> setOfClothes) {
        this.name = name;
        setSetOfClothes(setOfClothes);
    }

    public ClothesSet(String name, List<Clothing> setOfClothes, User user) {
        this.name = name;
        setSetOfClothes(setOfClothes);
        this.user = user;
    }

    public void addClothing(Clothing clothing) {
        this.setOfClothes.add(clothing);
        clothing.setClothesSet(this);
    }

    public void removeClothing(Clothing clothing) {
        this.setOfClothes.remove(clothing);
        clothing.setClothesSet(null);
    }

    public void setSetOfClothes(List<Clothing> setOfClothes) {
        if (setOfClothes != null)
            setOfClothes.forEach(this::addClothing);
    }
}
