package ua.kpi.tef.essd.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "clothes_sets")
public class ClothesSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @OneToMany(mappedBy = "clothesSet", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Clothing> setOfClothes = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public ClothesSet() {
    }

    public ClothesSet(String name, Set<Clothing> setOfClothes, User user) {
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

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Clothing> getSetOfClothes() {
        return setOfClothes;
    }

    public void setSetOfClothes(Set<Clothing> setOfClothes) {
        if(setOfClothes != null)
            setOfClothes.forEach(this::addClothing);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
