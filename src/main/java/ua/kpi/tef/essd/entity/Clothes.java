package ua.kpi.tef.essd.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "clothes")
public class Clothes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clothes_set_id")
    private ClothesSet clothesSet;

    @OneToMany(mappedBy = "clothes")
    private List<Part> partsList;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Part> getPartsList() {
        return partsList;
    }

    public void setPartsList(List<Part> partsList) {
        this.partsList = partsList;
    }
}
