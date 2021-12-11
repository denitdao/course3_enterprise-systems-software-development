package ua.kpi.tef.essd.backend.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "roles")
@NoArgsConstructor
@Getter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @ManyToMany(mappedBy = "roles", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"clothes", "clothesSets", "orders", "roles"})
    private final List<User> users = new LinkedList<>();

    /**
     * Link both User and Role to each-other
     */
    public void addUser(User user) {
        user.addRole(this);
    }

    /**
     * Unlink both User and Role from each-other
     */
    public void removeUser(User user) {
        user.removeRole(this);
    }

}
