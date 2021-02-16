package ua.kpi.tef.essd.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "clothes_set")
public class ClothesSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @OneToMany(mappedBy = "clothesSet", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Clothing> clothesSet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public void addClothing(Clothing clothing) {
        clothing.setClothesSet(this);
        clothesSet.add(clothing);
    }

    public void removeClothing(Clothing clothing) {
        clothesSet.remove(clothing);
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

    public Set<Clothing> getClothesSet() {
        return clothesSet;
    }

    public void setClothesSet(Set<Clothing> clothesSet) {
        this.clothesSet = clothesSet;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
