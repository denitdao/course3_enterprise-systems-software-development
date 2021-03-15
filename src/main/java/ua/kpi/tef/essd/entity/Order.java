package ua.kpi.tef.essd.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@Getter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @Setter
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clothing_id")
    @Setter
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
