package ua.kpi.tef.essd.entity;

import lombok.ToString;

@ToString
public enum OrderStatus {
    PENDING("Pending"), CONFIRMED("Confirmed"), SHIPPED("Shipped"),
    DELIVERED("Delivered"), CANCELLED("Cancelled");

    private final String title;

    OrderStatus(String title) {
        this.title = title;
    }
}
