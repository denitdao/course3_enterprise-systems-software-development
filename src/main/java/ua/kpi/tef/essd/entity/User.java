package ua.kpi.tef.essd.entity;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private Integer age;

    private String description;

    @OneToMany(mappedBy = "user", cascade = CascadeType.MERGE, orphanRemoval = true)
    private final List<Clothing> clothes = new LinkedList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.MERGE, orphanRemoval = true)
    private final List<ClothesSet> clothesSets = new LinkedList<>();

    public User() {
    }

    public User(String name, Integer age, String description) {
        this.name = name;
        this.age = age;
        this.description = description;
    }

    public User(String name, Integer age, String description, List<Clothing> clothes, List<ClothesSet> clothesSets) {
        this.name = name;
        this.age = age;
        this.description = description;
        setClothes(clothes);
        setClothesSets(clothesSets);
    }

    /**
     * Link both Clothing and User to each-other
     */
    public void addClothing(Clothing clothing) {
        this.clothes.add(clothing);
        clothing.setUser(this);
    }

    /**
     * Unlink both Clothing and User from each-other
     */
    public void removeClothing(Clothing clothing) {
        this.clothes.remove(clothing);
        clothing.setUser(null);
    }

    /**
     * Link both ClothesSet and User to each-other
     */
    public void addClothesSet(ClothesSet clothesSet) {
        this.clothesSets.add(clothesSet);
        clothesSet.setUser(this);
    }

    /**
     * Unlink both ClothesSet and User from each-other
     */
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

    public List<Clothing> getClothes() {
        return clothes;
    }

    public void setClothes(List<Clothing> clothes) {
        if (clothes != null)
            clothes.forEach(this::addClothing);
    }

    public List<ClothesSet> getClothesSets() {
        return clothesSets;
    }

    public void setClothesSets(List<ClothesSet> clothesSets) {
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
