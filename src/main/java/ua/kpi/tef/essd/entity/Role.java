package ua.kpi.tef.essd.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "roles")
@NoArgsConstructor
@Getter
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;

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
