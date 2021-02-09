package ua.kpi.tef.essd.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private int age;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Clothes> clothes;

    public User() {}

    public User(String name, int age) {
        this.name = name;
        this.age = age;
        clothes = new ArrayList<>();
    }

    public void addClothes(Clothes item){
        item.setUser(this);
        clothes.add(item);
    }

    public void removeClothes(Clothes item) {
        clothes.remove(item);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<Clothes> getClothes() {
        return clothes;
    }

    public void setClothes(List<Clothes> clothes) {
        this.clothes = clothes;
    }
}
