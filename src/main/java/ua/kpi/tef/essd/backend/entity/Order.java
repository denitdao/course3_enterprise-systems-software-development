package ua.kpi.tef.essd.backend.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@Getter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@EqualsAndHashCode
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @Setter
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "clothes", "clothesSets", "orders", "roles"})
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clothing_id")
    @Setter
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "parts", "orders", "clothesSet", "user"})
    private Clothing clothing;

    @Setter
    private Integer amount;

    @Setter
    private OrderStatus status;

    public Order(User user, Clothing clothing, Integer amount, OrderStatus status) {
        this.user = user;
        this.clothing = clothing;
        this.amount = amount;
        this.status = status;
    }
}
