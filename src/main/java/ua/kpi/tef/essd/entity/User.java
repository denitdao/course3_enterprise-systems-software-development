package ua.kpi.tef.essd.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private short age;

    private String description;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Clothing> clothes = new HashSet<>();

    public void addClothing(Clothing clothing){
        clothing.setUser(this);
        clothes.add(clothing);
    }

    public void removeClothing(Clothing clothing) {
        clothes.remove(clothing);
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

    public short getAge() {
        return age;
    }

    public void setAge(short age) {
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
        this.clothes = clothes;
    }

}
