package ua.kpi.tef.essd.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private Integer age;

    private String description;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Clothing> clothes = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ClothesSet> clothesSets = new HashSet<>();

    public User() {
    }

    public User(String name, Integer age, String description, Set<Clothing> clothes, Set<ClothesSet> clothesSets) {
        this.name = name;
        this.age = age;
        this.description = description;
        setClothes(clothes);
        setClothesSets(clothesSets);
    }

    public void addClothing(Clothing clothing) {
        this.clothes.add(clothing);
        clothing.setUser(this);
    }

    public void removeClothing(Clothing clothing) {
        this.clothes.remove(clothing);
        clothing.setUser(null);
    }

    public void addClothesSet(ClothesSet clothesSet) {
        this.clothesSets.add(clothesSet);
        clothesSet.setUser(this);
    }

    public void removeClothesSet(ClothesSet clothesSet) {
        this.clothesSets.remove(clothesSet);
        clothesSet.setUser(null);
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Clothing> getClothes() {
        return clothes;
    }

    public void setClothes(Set<Clothing> clothes) {
        if (clothes != null)
            clothes.forEach(this::addClothing);
    }

    public Set<ClothesSet> getClothesSets() {
        return clothesSets;
    }

    public void setClothesSets(Set<ClothesSet> clothesSets) {
        if (clothesSets != null)
            clothesSets.forEach(this::addClothesSet);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", description='" + description + '\'' +
                ", clothes={" + clothes.stream().map(Clothing::getName).collect(Collectors.joining(" | ")) +
                "}, clothesSets={" + clothesSets.stream().map(ClothesSet::getName).collect(Collectors.joining(" | ")) +
                "}}";
    }
}
